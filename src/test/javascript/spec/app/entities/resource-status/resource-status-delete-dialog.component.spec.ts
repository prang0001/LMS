/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsAppTestModule } from '../../../test.module';
import { ResourceStatusDeleteDialogComponent } from 'app/entities/resource-status/resource-status-delete-dialog.component';
import { ResourceStatusService } from 'app/entities/resource-status/resource-status.service';

describe('Component Tests', () => {
    describe('ResourceStatus Management Delete Component', () => {
        let comp: ResourceStatusDeleteDialogComponent;
        let fixture: ComponentFixture<ResourceStatusDeleteDialogComponent>;
        let service: ResourceStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [ResourceStatusDeleteDialogComponent]
            })
                .overrideTemplate(ResourceStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResourceStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourceStatusService);
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
