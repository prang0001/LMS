/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { RentalTransactionDetailComponent } from 'app/entities/rental-transaction/rental-transaction-detail.component';
import { RentalTransaction } from 'app/shared/model/rental-transaction.model';

describe('Component Tests', () => {
    describe('RentalTransaction Management Detail Component', () => {
        let comp: RentalTransactionDetailComponent;
        let fixture: ComponentFixture<RentalTransactionDetailComponent>;
        const route = ({ data: of({ rentalTransaction: new RentalTransaction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [RentalTransactionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RentalTransactionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RentalTransactionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rentalTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
