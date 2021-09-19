import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { IRentalTransaction, RentalTransaction } from 'app/shared/model/rental-transaction.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import index from '@angular/cli/lib/cli';
import { ActivatedRoute } from '@angular/router';

import { ByRentalTransactionService } from './by-rental-transaction.service';
import { LibraryResourceService } from 'app/entities/library-resource';

@Component({
    selector: 'jhi-by-rental-transaction-detail',
    templateUrl: './by-rental-transaction-detail.component.html'
})
export class ByRentalTransactionDetailComponent implements OnInit, OnDestroy {
    libraryResources: ILibraryResource[];
    byRentalTransactions: IRentalTransaction[];
    byRentalTransaction: IRentalTransaction;
    byStatLibR: ILibraryResource;
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
        private activatedRoute: ActivatedRoute,
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

    previousState() {
        window.history.back();
    }

    loadAll() {
        this.activatedRoute.data.subscribe(({ byRentalTransaction }) => {
            this.byRentalTransaction = byRentalTransaction;
            console.log(this.byRentalTransaction);
        });
    }

    reset() {
        this.page = 0;
        this.libraryResources = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLibraryResources();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLibraryResources() {
        this.eventSubscriber = this.eventManager.subscribe('libraryResourceListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateLibraryResources(data: ILibraryResource[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.libraryResources.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
