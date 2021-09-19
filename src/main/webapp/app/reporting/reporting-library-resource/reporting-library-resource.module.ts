import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from '../../shared';
import { ReportingLibraryResourceComponent, ReportingLibraryResourceRoute, reportingLibraryResourceRoute } from './';

const REPORTING_STATES = [...reportingLibraryResourceRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(REPORTING_STATES)],
    declarations: [ReportingLibraryResourceComponent],
    entryComponents: [ReportingLibraryResourceComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportingLibraryResourceModule {}
