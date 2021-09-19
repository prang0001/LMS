import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from '../../app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { SearchModule } from '../search/search.module';

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild([HOME_ROUTE]), SearchModule],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppHomeModule {}
