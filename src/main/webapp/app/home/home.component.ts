import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { LoginModalService, Principal, Account } from 'app/core';
import { throws } from 'assert';
import { RentalTransactionService } from '../entities/rental-transaction/rental-transaction.service';
import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    searchTypes: any[] = [];
    searchType: string;
    searchData: string;
    rentalTransactions: IRentalTransaction[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private rentalTransactionService: RentalTransactionService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.searchTypes.push('--Select Search Type--');
        this.searchTypes.push('Author');
        this.searchTypes.push('Resource ID');
        this.searchTypes.push('Title');
        this.searchTypes.push('Call Number');
        this.searchTypes.push('Description');
        this.searchTypes.push('Subject');
        this.searchType = '--Select Search Type--';
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private onSaveSuccess(res: any) {
        console.log(JSON.stringify(res));
    }

    private onError(errorMessage: string) {
        console.log('Error loading rentals for logged in user...');
    }
}
