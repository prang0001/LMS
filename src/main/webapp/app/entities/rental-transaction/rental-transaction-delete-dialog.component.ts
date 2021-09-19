import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';
import { RentalTransactionService } from './rental-transaction.service';

@Component({
    selector: 'jhi-rental-transaction-delete-dialog',
    templateUrl: './rental-transaction-delete-dialog.component.html'
})
export class RentalTransactionDeleteDialogComponent {
    rentalTransaction: IRentalTransaction;

    constructor(
        private rentalTransactionService: RentalTransactionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rentalTransactionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rentalTransactionListModification',
                content: 'Deleted an rentalTransaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rental-transaction-delete-popup',
    template: ''
})
export class RentalTransactionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rentalTransaction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RentalTransactionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.rentalTransaction = rentalTransaction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
