/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { WaitingListUpdateComponent } from 'app/entities/waiting-list/waiting-list-update.component';
import { WaitingListService } from 'app/entities/waiting-list/waiting-list.service';
import { WaitingList } from 'app/shared/model/waiting-list.model';

describe('Component Tests', () => {
    describe('WaitingList Management Update Component', () => {
        let comp: WaitingListUpdateComponent;
        let fixture: ComponentFixture<WaitingListUpdateComponent>;
        let service: WaitingListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [WaitingListUpdateComponent]
            })
                .overrideTemplate(WaitingListUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WaitingListUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WaitingListService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WaitingList(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.waitingList = entity;
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
                    const entity = new WaitingList();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.waitingList = entity;
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
