import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';
import { Principal } from 'app/core';
import { ByRentalTransactionService } from './by-rental-transaction.service';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-by-rental-transaction',
    templateUrl: './by-rental-transaction.component.html'
})
export class ByRentalTransactionComponent implements OnInit, OnDestroy {
    byRentalTransactions: IRentalTransaction[];
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
        private byRentalTransactionService: ByRentalTransactionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.byRentalTransactions = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.byRentalTransactionService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IRentalTransaction[]>) => this.paginateRentalTransactions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRentalTransactions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRentalTransaction) {
        return item.id;
    }

    registerChangeInRentalTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('rentalTransactionListModification', response => this.reset());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    previousState() {
        window.history.back();
    }

    reset() {
        this.page = 0;
        this.byRentalTransactions = [];
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

    private paginateRentalTransactions(data: IRentalTransaction[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.byRentalTransactions.push(data[i]);
        }
    }
}
