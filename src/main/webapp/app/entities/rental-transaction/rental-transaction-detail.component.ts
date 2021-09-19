import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';

@Component({
    selector: 'jhi-rental-transaction-detail',
    templateUrl: './rental-transaction-detail.component.html'
})
export class RentalTransactionDetailComponent implements OnInit {
    rentalTransaction: IRentalTransaction;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rentalTransaction }) => {
            this.rentalTransaction = rentalTransaction;
        });
    }

    previousState() {
        window.history.back();
    }
}
