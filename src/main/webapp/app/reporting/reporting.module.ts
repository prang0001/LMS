import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ReportingLibraryResourceModule } from './reporting-library-resource/reporting-library-resource.module';
import { ByStatusModule } from './reporting-library-resource/by-status/by-status.module';
import { ByWaitingListModule } from 'app/reporting/reporting-library-resource/by-waiting-list/by-waiting-list.module';
import { ByRentalTransactionModule } from 'app/reporting/reporting-library-resource/by-rental-transaction/by-rental-transaction.module';
import { ByResourceTypeModule } from 'app/reporting/reporting-library-resource/by-resource-type/by-resource-type.module';

@NgModule({
    // prettier-ignore
    imports: [
        ReportingLibraryResourceModule, ByStatusModule, ByWaitingListModule, ByRentalTransactionModule, ByResourceTypeModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportingModule {}
