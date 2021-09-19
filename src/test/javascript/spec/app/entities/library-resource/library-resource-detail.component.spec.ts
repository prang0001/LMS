/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { LibraryResourceDetailComponent } from 'app/entities/library-resource/library-resource-detail.component';
import { LibraryResource } from 'app/shared/model/library-resource.model';

describe('Component Tests', () => {
    describe('LibraryResource Management Detail Component', () => {
        let comp: LibraryResourceDetailComponent;
        let fixture: ComponentFixture<LibraryResourceDetailComponent>;
        const route = ({ data: of({ libraryResource: new LibraryResource(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [LibraryResourceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LibraryResourceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LibraryResourceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.libraryResource).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
