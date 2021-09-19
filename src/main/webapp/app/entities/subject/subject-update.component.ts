import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';

@Component({
    selector: 'jhi-subject-update',
    templateUrl: './subject-update.component.html'
})
export class SubjectUpdateComponent implements OnInit {
    subject: ISubject;
    isSaving: boolean;

    constructor(private subjectService: SubjectService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ subject }) => {
            this.subject = subject;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.subject.id !== undefined) {
            this.subscribeToSaveResponse(this.subjectService.update(this.subject));
        } else {
            this.subscribeToSaveResponse(this.subjectService.create(this.subject));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISubject>>) {
        result.subscribe((res: HttpResponse<ISubject>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
