/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { LibraryResourceUpdateComponent } from 'app/entities/library-resource/library-resource-update.component';
import { LibraryResourceService } from 'app/entities/library-resource/library-resource.service';
import { LibraryResource } from 'app/shared/model/library-resource.model';

describe('Component Tests', () => {
    describe('LibraryResource Management Update Component', () => {
        let comp: LibraryResourceUpdateComponent;
        let fixture: ComponentFixture<LibraryResourceUpdateComponent>;
        let service: LibraryResourceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [LibraryResourceUpdateComponent]
            })
                .overrideTemplate(LibraryResourceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LibraryResourceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryResourceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LibraryResource(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.libraryResource = entity;
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
                    const entity = new LibraryResource();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.libraryResource = entity;
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
