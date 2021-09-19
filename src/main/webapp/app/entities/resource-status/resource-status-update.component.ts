import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IResourceStatus } from 'app/shared/model/resource-status.model';
import { ResourceStatusService } from './resource-status.service';

@Component({
    selector: 'jhi-resource-status-update',
    templateUrl: './resource-status-update.component.html'
})
export class ResourceStatusUpdateComponent implements OnInit {
    resourceStatus: IResourceStatus;
    isSaving: boolean;

    constructor(private resourceStatusService: ResourceStatusService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resourceStatus }) => {
            this.resourceStatus = resourceStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resourceStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.resourceStatusService.update(this.resourceStatus));
        } else {
            this.subscribeToSaveResponse(this.resourceStatusService.create(this.resourceStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResourceStatus>>) {
        result.subscribe((res: HttpResponse<IResourceStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
