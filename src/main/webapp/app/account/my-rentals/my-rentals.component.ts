import { Component, OnInit, OnDestroy } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Principal, AccountService, JhiLanguageHelper } from 'app/core';
import { RentalTransactionService } from '../../entities/rental-transaction/rental-transaction.service';
import { IRentalTransaction, RentalTransaction } from '../../../app/shared/model/rental-transaction.model';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs';

@Component({
    selector: 'jhi-my-rentals',
    templateUrl: './my-rentals.component.html'
})
export class MyRentalsComponent implements OnInit, OnDestroy {
    error: string;
    success: string;
    rentalTransactions: IRentalTransaction[];
    eventSubscriber: Subscription;
    page: any;
    account: Account;

    constructor(
        private accountService: AccountService,
        private principal: Principal,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        protected rts: RentalTransactionService,
        private eventManager: JhiEventManager,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.rentalTransactions = [];
        this.registerChangeInRentalResources();
        this.principal.identity().then(account => {
            this.getRentalsForUser(account.login);
            this.account = account;
            console.log('Account:');
            console.log(JSON.stringify(this.account));
        });
        // this.getRentalsForUser('vangav');
    }

    getRentalsForUser(login: string) {
        console.log('Getting Rentals for user:');
        console.log(JSON.stringify(login));
        this.rts
            .findByLogin(login)
            .subscribe(
                (res: HttpResponse<IRentalTransaction[]>) => this.mapRentals(res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    mapRentals(res: IRentalTransaction[]) {
        console.log('My Rentals Service Response:');
        console.log(JSON.stringify(res));
        this.rentalTransactions = res;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    registerChangeInRentalResources() {
        this.eventSubscriber = this.eventManager.subscribe('rs', response => this.reset());
    }

    reset() {
        this.page = 0;
    }
}
