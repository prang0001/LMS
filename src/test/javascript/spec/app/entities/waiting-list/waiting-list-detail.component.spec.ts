/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { WaitingListDetailComponent } from 'app/entities/waiting-list/waiting-list-detail.component';
import { WaitingList } from 'app/shared/model/waiting-list.model';

describe('Component Tests', () => {
    describe('WaitingList Management Detail Component', () => {
        let comp: WaitingListDetailComponent;
        let fixture: ComponentFixture<WaitingListDetailComponent>;
        const route = ({ data: of({ waitingList: new WaitingList(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [WaitingListDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WaitingListDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WaitingListDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.waitingList).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
