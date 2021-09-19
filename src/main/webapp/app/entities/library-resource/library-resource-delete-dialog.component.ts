import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { LibraryResourceService } from './library-resource.service';

@Component({
    selector: 'jhi-library-resource-delete-dialog',
    templateUrl: './library-resource-delete-dialog.component.html'
})
export class LibraryResourceDeleteDialogComponent {
    libraryResource: ILibraryResource;

    constructor(
        private libraryResourceService: LibraryResourceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.libraryResourceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'libraryResourceListModification',
                content: 'Deleted an libraryResource'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-library-resource-delete-popup',
    template: ''
})
export class LibraryResourceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ libraryResource }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LibraryResourceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.libraryResource = libraryResource;
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
