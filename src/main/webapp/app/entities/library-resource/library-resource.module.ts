import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import {
    LibraryResourceComponent,
    LibraryResourceDetailComponent,
    LibraryResourceUpdateComponent,
    LibraryResourceDeletePopupComponent,
    LibraryResourceDeleteDialogComponent,
    libraryResourceRoute,
    libraryResourcePopupRoute
} from './';
import { ReserveResourceComponent } from '../../shared/reserve-resource/reserve-resource.component';

const ENTITY_STATES = [...libraryResourceRoute, ...libraryResourcePopupRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LibraryResourceComponent,
        LibraryResourceDetailComponent,
        LibraryResourceUpdateComponent,
        LibraryResourceDeleteDialogComponent,
        LibraryResourceDeletePopupComponent,
        ReserveResourceComponent
    ],
    entryComponents: [
        LibraryResourceComponent,
        LibraryResourceUpdateComponent,
        LibraryResourceDeleteDialogComponent,
        LibraryResourceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppLibraryResourceModule {}
