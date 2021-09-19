import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IResourceType } from 'app/shared/model/resource-type.model';
import { Principal } from 'app/core';
import { ByResourceTypeService } from './by-resource-type.service';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-by-resource-type',
    templateUrl: './by-resource-type.component.html'
})
export class ByResourceTypeComponent implements OnInit, OnDestroy {
    byResourceTypes: IResourceType[];
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
        private byResourceTypeService: ByResourceTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.byResourceTypes = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.byResourceTypeService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IResourceType[]>) => this.paginateResourceTypes(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInResourceTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResourceType) {
        return item.id;
    }

    registerChangeInResourceTypes() {
        this.eventSubscriber = this.eventManager.subscribe('resourceTypeListModification', response => this.reset());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    previousState() {
        window.history.back();
    }

    reset() {
        this.page = 0;
        this.byResourceTypes = [];
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

    private paginateResourceTypes(data: IResourceType[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.byResourceTypes.push(data[i]);
        }
    }
}
