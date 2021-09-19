/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsAppTestModule } from '../../../test.module';
import { WaitingListDeleteDialogComponent } from 'app/entities/waiting-list/waiting-list-delete-dialog.component';
import { WaitingListService } from 'app/entities/waiting-list/waiting-list.service';

describe('Component Tests', () => {
    describe('WaitingList Management Delete Component', () => {
        let comp: WaitingListDeleteDialogComponent;
        let fixture: ComponentFixture<WaitingListDeleteDialogComponent>;
        let service: WaitingListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [WaitingListDeleteDialogComponent]
            })
                .overrideTemplate(WaitingListDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WaitingListDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WaitingListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
