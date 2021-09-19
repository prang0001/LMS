/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { RentalTransactionUpdateComponent } from 'app/entities/rental-transaction/rental-transaction-update.component';
import { RentalTransactionService } from 'app/entities/rental-transaction/rental-transaction.service';
import { RentalTransaction } from 'app/shared/model/rental-transaction.model';

describe('Component Tests', () => {
    describe('RentalTransaction Management Update Component', () => {
        let comp: RentalTransactionUpdateComponent;
        let fixture: ComponentFixture<RentalTransactionUpdateComponent>;
        let service: RentalTransactionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [RentalTransactionUpdateComponent]
            })
                .overrideTemplate(RentalTransactionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RentalTransactionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RentalTransactionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RentalTransaction(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rentalTransaction = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RentalTransaction();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rentalTransaction = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
