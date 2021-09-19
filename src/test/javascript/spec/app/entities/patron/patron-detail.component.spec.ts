/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { PatronDetailComponent } from 'app/entities/patron/patron-detail.component';
import { Patron } from 'app/shared/model/patron.model';

describe('Component Tests', () => {
    describe('Patron Management Detail Component', () => {
        let comp: PatronDetailComponent;
        let fixture: ComponentFixture<PatronDetailComponent>;
        const route = ({ data: of({ patron: new Patron(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [PatronDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PatronDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PatronDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.patron).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
