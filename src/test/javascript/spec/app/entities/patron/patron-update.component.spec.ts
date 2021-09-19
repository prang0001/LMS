/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { PatronUpdateComponent } from 'app/entities/patron/patron-update.component';
import { PatronService } from 'app/entities/patron/patron.service';
import { Patron } from 'app/shared/model/patron.model';

describe('Component Tests', () => {
    describe('Patron Management Update Component', () => {
        let comp: PatronUpdateComponent;
        let fixture: ComponentFixture<PatronUpdateComponent>;
        let service: PatronService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [PatronUpdateComponent]
            })
                .overrideTemplate(PatronUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PatronUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatronService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Patron(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.patron = entity;
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
                    const entity = new Patron();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.patron = entity;
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
