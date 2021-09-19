import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import { ByStatusComponent, ByStatusDetailComponent, ByStatusRoute } from './';

const REPORT_STATES = [...ByStatusRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(REPORT_STATES)],
    declarations: [ByStatusComponent, ByStatusDetailComponent],
    entryComponents: [ByStatusComponent, ByStatusDetailComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ByStatusModule {}
