import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { LibraryResourceService } from './library-resource.service';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject';
import { IResourceStatus } from 'app/shared/model/resource-status.model';
import { ResourceStatusService } from 'app/entities/resource-status';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { ResourceTypeService } from 'app/entities/resource-type';
import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';
import { RentalTransactionService } from 'app/entities/rental-transaction';
import { IWaitingList } from 'app/shared/model/waiting-list.model';
import { WaitingListService } from 'app/entities/waiting-list';

@Component({
    selector: 'jhi-library-resource-update',
    templateUrl: './library-resource-update.component.html'
})
export class LibraryResourceUpdateComponent implements OnInit {
    libraryResource: ILibraryResource;
    isSaving: boolean;

    authors: IAuthor[];

    subjects: ISubject[];

    resourcestatuses: IResourceStatus[];

    resourcetypes: IResourceType[];

    rentaltransactions: IRentalTransaction[];

    waitinglists: IWaitingList[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private libraryResourceService: LibraryResourceService,
        private authorService: AuthorService,
        private subjectService: SubjectService,
        private resourceStatusService: ResourceStatusService,
        private resourceTypeService: ResourceTypeService,
        private rentalTransactionService: RentalTransactionService,
        private waitingListService: WaitingListService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ libraryResource }) => {
            this.libraryResource = libraryResource;
        });
        this.authorService.query().subscribe(
            (res: HttpResponse<IAuthor[]>) => {
                this.authors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.subjectService.query().subscribe(
            (res: HttpResponse<ISubject[]>) => {
                this.subjects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.resourceStatusService.query().subscribe(
            (res: HttpResponse<IResourceStatus[]>) => {
                this.resourcestatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.resourceTypeService.query().subscribe(
            (res: HttpResponse<IResourceType[]>) => {
                this.resourcetypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rentalTransactionService.query().subscribe(
            (res: HttpResponse<IRentalTransaction[]>) => {
                this.rentaltransactions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.waitingListService.query().subscribe(
            (res: HttpResponse<IWaitingList[]>) => {
                this.waitinglists = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.libraryResource.id !== undefined) {
            this.subscribeToSaveResponse(this.libraryResourceService.update(this.libraryResource));
        } else {
            this.subscribeToSaveResponse(this.libraryResourceService.create(this.libraryResource));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILibraryResource>>) {
        result.subscribe((res: HttpResponse<ILibraryResource>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAuthorById(index: number, item: IAuthor) {
        return item.id;
    }

    trackSubjectById(index: number, item: ISubject) {
        return item.id;
    }

    trackResourceStatusById(index: number, item: IResourceStatus) {
        return item.id;
    }

    trackResourceTypeById(index: number, item: IResourceType) {
        return item.id;
    }

    trackRentalTransactionById(index: number, item: IRentalTransaction) {
        return item.id;
    }

    trackWaitingListById(index: number, item: IWaitingList) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
