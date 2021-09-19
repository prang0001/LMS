/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsAppTestModule } from '../../../test.module';
import { PatronDeleteDialogComponent } from 'app/entities/patron/patron-delete-dialog.component';
import { PatronService } from 'app/entities/patron/patron.service';

describe('Component Tests', () => {
    describe('Patron Management Delete Component', () => {
        let comp: PatronDeleteDialogComponent;
        let fixture: ComponentFixture<PatronDeleteDialogComponent>;
        let service: PatronService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [PatronDeleteDialogComponent]
            })
                .overrideTemplate(PatronDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PatronDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatronService);
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
