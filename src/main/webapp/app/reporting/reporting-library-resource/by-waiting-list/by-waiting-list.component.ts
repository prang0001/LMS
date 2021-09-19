import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IWaitingList } from 'app/shared/model/waiting-list.model';
import { Principal } from 'app/core';
import { ByWaitingListService } from './by-waiting-list.service';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-by-waiting-list',
    templateUrl: './by-waiting-list.component.html'
})
export class ByWaitingListComponent implements OnInit, OnDestroy {
    byWaitingLists: IWaitingList[];
    libraryResource: ILibraryResource;
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
        private byWaitingListService: ByWaitingListService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.byWaitingLists = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.byWaitingListService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IWaitingList[]>) => this.paginateWaitingLists(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWaitingLists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWaitingList) {
        return item.id;
    }

    registerChangeInWaitingLists() {
        this.eventSubscriber = this.eventManager.subscribe('waitingListListModification', response => this.reset());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    previousState() {
        window.history.back();
    }

    reset() {
        this.page = 0;
        this.byWaitingLists = [];
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

    private paginateWaitingLists(data: IWaitingList[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.byWaitingLists.push(data[i]);
        }
    }
}
