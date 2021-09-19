import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { Principal } from '../../core/index';

import { ITEMS_PER_PAGE } from '../../shared/index';

@Component({
    selector: 'jhi-reporting-library-resource',
    templateUrl: './reporting-library-resource.component.html'
})
export class ReportingLibraryResourceComponent implements OnInit {
    currentAccount: any;

    constructor() {}

    ngOnInit() {}
}
