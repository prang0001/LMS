import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResourceStatus } from 'app/shared/model/resource-status.model';

@Component({
    selector: 'jhi-resource-status-detail',
    templateUrl: './resource-status-detail.component.html'
})
export class ResourceStatusDetailComponent implements OnInit {
    resourceStatus: IResourceStatus;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resourceStatus }) => {
            this.resourceStatus = resourceStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
