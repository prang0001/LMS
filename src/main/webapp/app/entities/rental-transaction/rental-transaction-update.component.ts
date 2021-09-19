import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IRentalTransaction, RentalTransaction } from 'app/shared/model/rental-transaction.model';
import { RentalTransactionService } from './rental-transaction.service';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { LibraryResourceService } from 'app/entities/library-resource';
import { IPatron } from 'app/shared/model/patron.model';
import { PatronService } from 'app/entities/patron';

@Component({
    selector: 'jhi-rental-transaction-update',
    templateUrl: './rental-transaction-update.component.html'
})
export class RentalTransactionUpdateComponent implements OnInit {
    rentalTransaction: IRentalTransaction;
    isSaving: boolean;

    libraryresources: ILibraryResource[];

    patrons: IPatron[];
    rentalStartDateDp: any;
    rentalDueDateDp: any;
    rentalFinalDueDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private rentalTransactionService: RentalTransactionService,
        private libraryResourceService: LibraryResourceService,
        private patronService: PatronService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rentalTransaction }) => {
            this.rentalTransaction = rentalTransaction;
        });
        this.libraryResourceService.query().subscribe(
            (res: HttpResponse<ILibraryResource[]>) => {
                this.libraryresources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.patronService.query().subscribe(
            (res: HttpResponse<IPatron[]>) => {
                this.patrons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rentalTransaction.id !== undefined) {
            this.updateRentalTransaction();
            // this.subscribeToSaveResponse(this.rentalTransactionService.update(this.rentalTransaction));
        } else {
            this.createNewRentalTransaction();
            // this.subscribeToSaveResponse(this.rentalTransactionService.create(this.rentalTransaction));
        }
    }

    updateRentalTransaction() {
        console.log('Updating rental transaction...');
        console.log('Current rental transaction: ');
        console.log(JSON.stringify(this.rentalTransaction));
        if (this.rentalTransaction.extendRental) {
            const dueDate = moment(this.rentalTransaction.rentalDueDate, 'DD-MM-YYYY').add(
                'days',
                this.rentalTransaction.resourceType.rentalDuration
            );
            this.rentalTransaction.dueDate = dueDate;
        }
        this.subscribeToSaveResponse(this.rentalTransactionService.update(this.rentalTransaction));
    }

    createNewRentalTransaction() {
        console.log('Creating new rental transaction...');
        console.log('Current rental transaction: ');
        console.log(JSON.stringify(this.rentalTransaction));
        const newRt = new RentalTransaction();
        newRt.rentalStartDateDp = this.rentalTransaction.rentalDueDateDp;
        newRt.patron = this.rentalTransaction.patron;
        newRt.libraryResources = [];
        newRt.libraryResources = this.rentalTransaction.libraryResources;
        newRt.rentalPeriod = this.rentalTransaction.libraryResources[0].resourceType.rentalDuration;
        newRt.overdue = false;
        newRt.finesOverdue = 0;
        newRt.daysOverdue = 0;
        newRt.rentalStartDate = moment();
        const dueDate = moment(newRt.rentalStartDate, 'DD-MM-YYYY').add('days', newRt.rentalPeriod);
        newRt.rentalDueDate = dueDate;
        const finalDueDate = moment(newRt.rentalStartDate, 'DD-MM-YYYY').add('days', 35);
        newRt.rentalFinalDueDate = finalDueDate;
        console.log('Rental transaction to be created: ');
        console.log(JSON.stringify(newRt));
        this.subscribeToSaveResponse(this.rentalTransactionService.create(newRt));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRentalTransaction>>) {
        result.subscribe((res: HttpResponse<IRentalTransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLibraryResourceById(index: number, item: ILibraryResource) {
        return item.id;
    }

    trackPatronById(index: number, item: IPatron) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
