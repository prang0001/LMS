import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import {
    WaitingListComponent,
    WaitingListDetailComponent,
    WaitingListUpdateComponent,
    WaitingListDeletePopupComponent,
    WaitingListDeleteDialogComponent,
    waitingListRoute,
    waitingListPopupRoute
} from './';

const ENTITY_STATES = [...waitingListRoute, ...waitingListPopupRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WaitingListComponent,
        WaitingListDetailComponent,
        WaitingListUpdateComponent,
        WaitingListDeleteDialogComponent,
        WaitingListDeletePopupComponent
    ],
    entryComponents: [WaitingListComponent, WaitingListUpdateComponent, WaitingListDeleteDialogComponent, WaitingListDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppWaitingListModule {}
