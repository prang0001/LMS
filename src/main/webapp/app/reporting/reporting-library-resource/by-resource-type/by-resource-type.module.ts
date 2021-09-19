import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import { ByResourceTypeComponent, ByResourceTypeDetailComponent, ByResourceTypeRoute } from './';

const REPORT_STATES = [...ByResourceTypeRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(REPORT_STATES)],
    declarations: [ByResourceTypeComponent, ByResourceTypeDetailComponent],
    entryComponents: [ByResourceTypeComponent, ByResourceTypeDetailComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ByResourceTypeModule {}
