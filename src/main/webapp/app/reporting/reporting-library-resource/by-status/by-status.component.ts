import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IResourceStatus } from 'app/shared/model/resource-status.model';
import { Principal } from 'app/core';
import { ByStatusService } from './by-status.service';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-by-status',
    templateUrl: './by-status.component.html'
})
export class ByStatusComponent implements OnInit, OnDestroy {
    byStatuses: IResourceStatus[];
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
        private byStatusService: ByStatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.byStatuses = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.byStatusService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IResourceStatus[]>) => this.paginateResourceStatuses(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInResourceStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResourceStatus) {
        return item.id;
    }

    registerChangeInResourceStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('resourceStatusListModification', response => this.reset());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    previousState() {
        window.history.back();
    }

    reset() {
        this.page = 0;
        this.byStatuses = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateResourceStatuses(data: IResourceStatus[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.byStatuses.push(data[i]);
        }
    }
}
