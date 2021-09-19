import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IWaitingList } from 'app/shared/model/waiting-list.model';
import { WaitingListService } from './waiting-list.service';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { LibraryResourceService } from 'app/entities/library-resource';
import { IPatron } from 'app/shared/model/patron.model';
import { PatronService } from 'app/entities/patron';

@Component({
    selector: 'jhi-waiting-list-update',
    templateUrl: './waiting-list-update.component.html'
})
export class WaitingListUpdateComponent implements OnInit {
    waitingList: IWaitingList;
    isSaving: boolean;

    libraryresources: ILibraryResource[];

    patrons: IPatron[];
    dateRequestDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private waitingListService: WaitingListService,
        private libraryResourceService: LibraryResourceService,
        private patronService: PatronService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ waitingList }) => {
            this.waitingList = waitingList;
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
        if (this.waitingList.id !== undefined) {
            this.subscribeToSaveResponse(this.waitingListService.update(this.waitingList));
        } else {
            this.subscribeToSaveResponse(this.waitingListService.create(this.waitingList));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWaitingList>>) {
        result.subscribe((res: HttpResponse<IWaitingList>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
