import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import { ByRentalTransactionComponent, ByRentalTransactionDetailComponent, ByRentalTransactionRoute } from './';

const REPORT_STATES = [...ByRentalTransactionRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(REPORT_STATES)],
    declarations: [ByRentalTransactionComponent, ByRentalTransactionDetailComponent],
    entryComponents: [ByRentalTransactionComponent, ByRentalTransactionDetailComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ByRentalTransactionModule {}
