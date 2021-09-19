import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE } from '../../shared/index';
import { SearchService } from './search.service';
import { ISearchResponse } from '../../shared/model/search-response-model';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchResponse } from '../../../app/shared/model/search-response-model';
import { ActivatedRoute } from '@angular/router';
import { IRentalTransaction, RentalTransaction } from '../../../app/shared/model/rental-transaction.model';
import { LibraryResource } from '../../../app/shared/model/library-resource.model';
import { Principal, AccountService, User } from '../../../app/core';
import { ILibraryResource } from '../../../app/shared/model/library-resource.model';
import { Moment } from 'moment';
import moment = require('moment');
import { Patron } from '../../../app/shared/model/patron.model';
import { RentalTransactionService } from '../../../app/entities/rental-transaction/rental-transaction.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LibraryResourceService } from '../../entities/library-resource/library-resource.service';

@Component({
    selector: 'jhi-search-results',
    templateUrl: './search-results.component.html'
})
export class SearchResultsComponent implements OnInit, OnDestroy {
    resourceToReserve: LibraryResource;
    rentalTransaction: IRentalTransaction;
    uAccount: any;
    user: User;
    libraryResource: ILibraryResource;
    searchResponse: SearchResponse;
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private searchService: SearchService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private rentalTransactionService: RentalTransactionService,
        private router: Router,
        private libraryResourceService: LibraryResourceService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ searchResponse }) => {
            this.searchResponse = searchResponse;
        });
        this.registerChangeInSearchResources();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    /*reset = function() {
        return of(new SearchResponse('12', 'jgjgjg', null));
    };*/

    registerChangeInSearchResources() {
        this.eventSubscriber = this.eventManager.subscribe('nhhh', response => this.reset());
    }
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    reserveResource(libResourceId) {
        console.log('Fetching lib resource from backend');
        if (libResourceId) {
            this.subscribeToLibResFindResponse(this.libraryResourceService.find(libResourceId));
        }
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

    private subscribeToLibResFindResponse(result: Observable<HttpResponse<ILibraryResource>>) {
        result.subscribe(
            (res: HttpResponse<ILibraryResource>) => {
                this.resourceToReserve = res.body;
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
                const dueDate = moment(rental.rentalStartDate, 'DD-MM-YYYY').add(
                    'days',
                    this.resourceToReserve.resourceType.rentalDuration
                );
                rental.rentalDueDate = dueDate;
                const finalDueDate = moment(rental.rentalStartDate, 'DD-MM-YYYY').add('days', 35);
                rental.rentalFinalDueDate = finalDueDate;
                const p = new Patron();
                p.id = 3;
                //p.login = this.uAccount.login;
                rental.patrons = [];
                rental.patrons.push(p);
                console.log('New Rental Transaction:');
                console.log(rental);
                this.rentalTransaction = rental;
                this.saveRental();
            },
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

    reset() {
        this.page = 0;
    }
}
