import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import {
    ResourceStatusComponent,
    ResourceStatusDetailComponent,
    ResourceStatusUpdateComponent,
    ResourceStatusDeletePopupComponent,
    ResourceStatusDeleteDialogComponent,
    resourceStatusRoute,
    resourceStatusPopupRoute
} from './';

const ENTITY_STATES = [...resourceStatusRoute, ...resourceStatusPopupRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResourceStatusComponent,
        ResourceStatusDetailComponent,
        ResourceStatusUpdateComponent,
        ResourceStatusDeleteDialogComponent,
        ResourceStatusDeletePopupComponent
    ],
    entryComponents: [
        ResourceStatusComponent,
        ResourceStatusUpdateComponent,
        ResourceStatusDeleteDialogComponent,
        ResourceStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppResourceStatusModule {}
