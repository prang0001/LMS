import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { Observable } from 'rxjs';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '../../../app/shared';
import { LoginModalService } from '../../../app/core';
import { Register } from './register.service';
import { IPatron, Patron } from '../../../app/shared/model/patron.model';
import { PatronService } from '../../entities/patron/patron.service';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    patron: IPatron = new Patron();

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private patronService: PatronService
    ) {}

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    register(patron: Patron) {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then(key => {
                this.registerAccount.langKey = key;
                this.createPatron(patron);
                this.registerService.save(this.registerAccount).subscribe(
                    () => {
                        this.success = true;
                    },
                    response => this.processError(response)
                );
            });
        }
    }

    createPatron(patron: Patron) {
        patron.patronStatus = 'Active';
        patron.login = this.registerAccount.login;
        patron.email = this.registerAccount.email;
        console.log('Creating Patron as part of Registration Process...');
        console.log(JSON.stringify(patron));
        this.subscribeToSaveResponse(this.patronService.create(patron));
        // this.patronService.create(patron);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPatron>>) {
        result.subscribe((res: HttpResponse<IPatron>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError(res));
    }

    private onSaveSuccess() {
        console.log('Successful Patron creation as part of the registration process...');
    }

    private onSaveError(res: HttpErrorResponse) {
        console.log('Error while creating a new patron as part of the registration process...');
        console.log(JSON.stringify(res));
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response: HttpErrorResponse) {
        this.success = null;
        if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
