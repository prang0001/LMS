import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import { ByWaitingListComponent, ByWaitingListDetailComponent, ByWaitingListRoute } from './';

const REPORT_STATES = [...ByWaitingListRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(REPORT_STATES)],
    declarations: [ByWaitingListComponent, ByWaitingListDetailComponent],
    entryComponents: [ByWaitingListComponent, ByWaitingListDetailComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ByWaitingListModule {}
