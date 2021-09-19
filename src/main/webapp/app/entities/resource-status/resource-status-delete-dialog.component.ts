import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResourceStatus } from 'app/shared/model/resource-status.model';
import { ResourceStatusService } from './resource-status.service';

@Component({
    selector: 'jhi-resource-status-delete-dialog',
    templateUrl: './resource-status-delete-dialog.component.html'
})
export class ResourceStatusDeleteDialogComponent {
    resourceStatus: IResourceStatus;

    constructor(
        private resourceStatusService: ResourceStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resourceStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resourceStatusListModification',
                content: 'Deleted an resourceStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resource-status-delete-popup',
    template: ''
})
export class ResourceStatusDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resourceStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResourceStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.resourceStatus = resourceStatus;
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
