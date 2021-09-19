import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import {
    ResourceTypeComponent,
    ResourceTypeDetailComponent,
    ResourceTypeUpdateComponent,
    ResourceTypeDeletePopupComponent,
    ResourceTypeDeleteDialogComponent,
    resourceTypeRoute,
    resourceTypePopupRoute
} from './';

const ENTITY_STATES = [...resourceTypeRoute, ...resourceTypePopupRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResourceTypeComponent,
        ResourceTypeDetailComponent,
        ResourceTypeUpdateComponent,
        ResourceTypeDeleteDialogComponent,
        ResourceTypeDeletePopupComponent
    ],
    entryComponents: [
        ResourceTypeComponent,
        ResourceTypeUpdateComponent,
        ResourceTypeDeleteDialogComponent,
        ResourceTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppResourceTypeModule {}
