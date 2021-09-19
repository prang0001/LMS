/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsAppTestModule } from '../../../test.module';
import { RentalTransactionDeleteDialogComponent } from 'app/entities/rental-transaction/rental-transaction-delete-dialog.component';
import { RentalTransactionService } from 'app/entities/rental-transaction/rental-transaction.service';

describe('Component Tests', () => {
    describe('RentalTransaction Management Delete Component', () => {
        let comp: RentalTransactionDeleteDialogComponent;
        let fixture: ComponentFixture<RentalTransactionDeleteDialogComponent>;
        let service: RentalTransactionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [RentalTransactionDeleteDialogComponent]
            })
                .overrideTemplate(RentalTransactionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RentalTransactionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RentalTransactionService);
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
