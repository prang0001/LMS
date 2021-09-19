import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import {
    PatronComponent,
    PatronDetailComponent,
    PatronUpdateComponent,
    PatronDeletePopupComponent,
    PatronDeleteDialogComponent,
    patronRoute,
    patronPopupRoute
} from './';

const ENTITY_STATES = [...patronRoute, ...patronPopupRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PatronComponent, PatronDetailComponent, PatronUpdateComponent, PatronDeleteDialogComponent, PatronDeletePopupComponent],
    entryComponents: [PatronComponent, PatronUpdateComponent, PatronDeleteDialogComponent, PatronDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppPatronModule {}
