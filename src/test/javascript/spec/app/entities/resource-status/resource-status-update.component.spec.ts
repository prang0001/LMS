/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { ResourceStatusUpdateComponent } from 'app/entities/resource-status/resource-status-update.component';
import { ResourceStatusService } from 'app/entities/resource-status/resource-status.service';
import { ResourceStatus } from 'app/shared/model/resource-status.model';

describe('Component Tests', () => {
    describe('ResourceStatus Management Update Component', () => {
        let comp: ResourceStatusUpdateComponent;
        let fixture: ComponentFixture<ResourceStatusUpdateComponent>;
        let service: ResourceStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [ResourceStatusUpdateComponent]
            })
                .overrideTemplate(ResourceStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResourceStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourceStatusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ResourceStatus(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resourceStatus = entity;
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
                    const entity = new ResourceStatus();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resourceStatus = entity;
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
