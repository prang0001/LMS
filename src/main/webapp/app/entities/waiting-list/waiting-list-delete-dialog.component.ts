import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWaitingList } from 'app/shared/model/waiting-list.model';
import { WaitingListService } from './waiting-list.service';

@Component({
    selector: 'jhi-waiting-list-delete-dialog',
    templateUrl: './waiting-list-delete-dialog.component.html'
})
export class WaitingListDeleteDialogComponent {
    waitingList: IWaitingList;

    constructor(
        private waitingListService: WaitingListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.waitingListService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'waitingListListModification',
                content: 'Deleted an waitingList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-waiting-list-delete-popup',
    template: ''
})
export class WaitingListDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ waitingList }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WaitingListDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.waitingList = waitingList;
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
