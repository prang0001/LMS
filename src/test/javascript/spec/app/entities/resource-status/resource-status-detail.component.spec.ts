/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LmsAppTestModule } from '../../../test.module';
import { ResourceStatusDetailComponent } from 'app/entities/resource-status/resource-status-detail.component';
import { ResourceStatus } from 'app/shared/model/resource-status.model';

describe('Component Tests', () => {
    describe('ResourceStatus Management Detail Component', () => {
        let comp: ResourceStatusDetailComponent;
        let fixture: ComponentFixture<ResourceStatusDetailComponent>;
        const route = ({ data: of({ resourceStatus: new ResourceStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LmsAppTestModule],
                declarations: [ResourceStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResourceStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResourceStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resourceStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
