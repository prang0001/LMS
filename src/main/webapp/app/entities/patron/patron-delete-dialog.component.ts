import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatron } from 'app/shared/model/patron.model';
import { PatronService } from './patron.service';

@Component({
    selector: 'jhi-patron-delete-dialog',
    templateUrl: './patron-delete-dialog.component.html'
})
export class PatronDeleteDialogComponent {
    patron: IPatron;

    constructor(private patronService: PatronService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.patronService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'patronListModification',
                content: 'Deleted an patron'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-patron-delete-popup',
    template: ''
})
export class PatronDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ patron }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PatronDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.patron = patron;
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
