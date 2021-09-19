/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LmsAppTestModule } from '../../../test.module';
import { LibraryResourceDeleteDialogComponent } from 'app/entities/library-resource/library-resource-delete-dialog.component';
import { LibraryResourceService } from 'app/entities/library-resource/library-resource.service';

describe('Component Tests', () => {
    describe('LibraryResource Management Delete Component', () => {
        let comp: LibraryResourceDeleteDialogComponent;
        let fixture: ComponentFixture<LibraryResourceDeleteDialogComponent>;
        let service: LibraryResourceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [LibraryResourceDeleteDialogComponent]
            })
                .overrideTemplate(LibraryResourceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LibraryResourceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryResourceService);
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
