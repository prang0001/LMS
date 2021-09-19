import { Component, OnDestroy, OnInit, Input } from '@angular/core';
import { LibraryResource } from '../model/library-resource.model';
import { IRentalTransaction, RentalTransaction } from '../../../app/shared/model/rental-transaction.model';
import { RentalTransactionService } from '../../../app/entities/rental-transaction/rental-transaction.service';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';
import { Moment } from 'moment';
import moment = require('moment');
import { Router } from '@angular/router';
import { Principal, AccountService, User } from '../../../app/core';
import { Patron, IPatron } from '../model/patron.model';
import { LibraryResourceService } from '../../entities/library-resource/library-resource.service';
import { ILibraryResource } from '../../../app/shared/model/library-resource.model';
import { PatronService } from '../../entities/patron/patron.service';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE } from '../../../app/shared';

@Component({
    selector: 'jhi-reserve-resource',
    templateUrl: './reserve-resource.component.html'
})
export class ReserveResourceComponent implements OnInit, OnDestroy {
    @Input('resourceToReserve') resourceToReserve: LibraryResource;
    rentalTransaction: IRentalTransaction;
    uAccount: any;
    user: User;
    libraryResource: ILibraryResource;
    patrons: IPatron[];
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    loggedInPatronId: number;

    constructor(
        private rentalTransactionService: RentalTransactionService,
        private datePipe: DatePipe,
        private router: Router,
        private account: AccountService,
        private principal: Principal,
        private libraryResourceService: LibraryResourceService,
        private patronService: PatronService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks
    ) {
        this.patrons = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        console.log('-----> Reserve-Resource Component Initialized...');
        this.principal.identity().then(account => {
            this.uAccount = account.login;
            // this.uAccount = this.copyAccount(account);
        });
        this.loadAllPatrons();
    }

    getPatronDetails() {}

    ngOnDestroy() {}

    reserveResource() {
        console.log('Reserving Resource...');
        console.log(this.resourceToReserve);
        const dateFormat = 'yyyy-MM-dd';
        const rental = new RentalTransaction();
        const lr = new LibraryResource();
        rental.libraryResources = [];
        rental.libraryResources.push(this.resourceToReserve);
        rental.rentalPeriod = this.resourceToReserve.resourceType.rentalDuration;
        rental.overdue = false;
        rental.finesOverdue = 0;
        rental.daysOverdue = 0;
        rental.rentalStartDate = moment();
        const dueDate = moment(rental.rentalStartDate, 'DD-MM-YYYY').add('days', this.resourceToReserve.resourceType.rentalDuration);
        rental.rentalDueDate = dueDate;
        const finalDueDate = moment(rental.rentalStartDate, 'DD-MM-YYYY').add('days', 35);
        rental.rentalFinalDueDate = finalDueDate;
        const p = new Patron();
        p.id = this.loggedInPatronId; // 11;
        // p.login = this.uAccount.login;
        rental.patrons = [];
        rental.patrons.push(p);
        console.log('New Rental Transaction:');
        console.log(rental);
        this.rentalTransaction = rental;
        this.saveRental();
    }

    saveRental() {
        this.subscribeToSaveResponse(this.rentalTransactionService.create(this.rentalTransaction));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRentalTransaction>>) {
        result.subscribe(
            (res: HttpResponse<IRentalTransaction>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(res: any) {
        console.log(JSON.stringify(res));
        this.router.navigate(['/rental-transaction', res.body.id, 'view']);
        this.updateLibraryResourceStatus(this.resourceToReserve);
    }

    private onSaveError() {
        console.log('Error when creating rental transaction...');
    }

    private onLrSaveSuccess() {
        console.log('Update of library resource status was successful...');
    }

    private onLrSaveError() {
        console.log('Error when updating library resource status...');
    }

    updateLibraryResourceStatus(lr: LibraryResource) {
        console.log('Updating Library Resource status after successful Reservation...');
        console.log(JSON.stringify(lr));
        lr.resourceStatus.id = 2;
        lr.resourceStatus.statusName = 'Rented';
        this.subscribeToLrSaveResponse(this.libraryResourceService.update(lr));
    }

    private subscribeToLrSaveResponse(result: Observable<HttpResponse<ILibraryResource>>) {
        result.subscribe((res: HttpResponse<ILibraryResource>) => this.onLrSaveSuccess(), (res: HttpErrorResponse) => this.onLrSaveError());
    }

    previousState() {
        window.history.back();
    }

    copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl
        };
    }

    loadAllPatrons() {
        this.patronService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IPatron[]>) => this.paginatePatrons(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginatePatrons(data: IPatron[], headers: HttpHeaders) {
        console.log('User logged in:');
        console.log(JSON.stringify(this.uAccount));
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.patrons.push(data[i]);
            // if(data[i].login === this.uAccount) {
            //     this.loggedInPatronId = data[i].id;
            // }
        }
        for (const p of this.patrons) {
            if (p.login === this.uAccount) {
                console.log('Found a patron that matches current logged in user...', p);
                this.loggedInPatronId = p.id;
            }
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
