import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPatron } from '../../../app/shared/model/patron.model';
import { PatronService } from './patron.service';
import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';
import { RentalTransactionService } from 'app/entities/rental-transaction';
import { IWaitingList } from 'app/shared/model/waiting-list.model';
import { WaitingListService } from 'app/entities/waiting-list';

@Component({
    selector: 'jhi-patron-update',
    templateUrl: './patron-update.component.html'
})
export class PatronUpdateComponent implements OnInit {
    patron: IPatron;
    isSaving: boolean;
    statuses: any[] = [];

    rentaltransactions: IRentalTransaction[];

    waitinglists: IWaitingList[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private patronService: PatronService,
        private rentalTransactionService: RentalTransactionService,
        private waitingListService: WaitingListService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patron }) => {
            this.patron = patron;
        });
        this.rentalTransactionService.query().subscribe(
            (res: HttpResponse<IRentalTransaction[]>) => {
                this.rentaltransactions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.waitingListService.query().subscribe(
            (res: HttpResponse<IWaitingList[]>) => {
                this.waitinglists = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.statuses.push('Active');
        this.statuses.push('InActive');
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.patron.id !== undefined) {
            this.subscribeToSaveResponse(this.patronService.update(this.patron));
        } else {
            this.subscribeToSaveResponse(this.patronService.create(this.patron));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPatron>>) {
        result.subscribe((res: HttpResponse<IPatron>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRentalTransactionById(index: number, item: IRentalTransaction) {
        return item.id;
    }

    trackWaitingListById(index: number, item: IWaitingList) {
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
