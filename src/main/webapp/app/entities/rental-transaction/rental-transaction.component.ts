import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs';

import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RentalTransactionService } from './rental-transaction.service';
import { Patron } from '../../shared/model/patron.model';
import { PatronService } from '../patron/patron.service';

@Component({
    selector: 'jhi-rental-transaction',
    templateUrl: './rental-transaction.component.html'
})
export class RentalTransactionComponent implements OnInit, OnDestroy {
    rentalTransactions: IRentalTransaction[];
    currentAccount: Account;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    loggedInPatron: Patron;

    constructor(
        private rentalTransactionService: RentalTransactionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private ps: PatronService
    ) {
        this.rentalTransactions = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.rentalTransactionService
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

    reset() {
        this.page = 0;
        this.rentalTransactions = [];
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
        this.ps.find(2).subscribe(
            (res: HttpResponse<Patron>) => {
                console.log('Patron Logged in:');
                this.loggedInPatron = res.body;
                console.log(JSON.stringify(this.loggedInPatron));
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
            this.rentalTransactions.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
