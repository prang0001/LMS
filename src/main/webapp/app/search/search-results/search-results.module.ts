import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from '../../shared';
import { SearchResultsComponent } from './search-results.component';
import { searchResultsRoute } from './search-results.route';
import { ReserveResourceComponent } from '../../shared/reserve-resource/reserve-resource.component';

const SEARCH_STATES = [...searchResultsRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(SEARCH_STATES)],
    declarations: [SearchResultsComponent],
    entryComponents: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SearchResultsModule {}
