import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SearchResultsModule } from './search-results/search-results.module';

@NgModule({
    // prettier-ignore
    imports: [
        SearchResultsModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SearchModule {}
