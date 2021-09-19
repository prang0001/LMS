import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LmsAppLibraryResourceModule } from './library-resource/library-resource.module';
import { LmsAppAuthorModule } from './author/author.module';
import { LmsAppSubjectModule } from './subject/subject.module';
import { LmsAppPatronModule } from './patron/patron.module';
import { LmsAppResourceStatusModule } from './resource-status/resource-status.module';
import { LmsAppResourceTypeModule } from './resource-type/resource-type.module';
import { LmsAppRentalTransactionModule } from './rental-transaction/rental-transaction.module';
import { LmsAppWaitingListModule } from './waiting-list/waiting-list.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        LmsAppLibraryResourceModule,
        LmsAppAuthorModule,
        LmsAppSubjectModule,
        LmsAppPatronModule,
        LmsAppResourceStatusModule,
        LmsAppResourceTypeModule,
        LmsAppRentalTransactionModule,
        LmsAppWaitingListModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppEntityModule {}
