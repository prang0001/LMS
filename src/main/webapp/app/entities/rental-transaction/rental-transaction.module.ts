import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LmsAppSharedModule } from 'app/shared';
import {
    RentalTransactionComponent,
    RentalTransactionDetailComponent,
    RentalTransactionUpdateComponent,
    RentalTransactionDeletePopupComponent,
    RentalTransactionDeleteDialogComponent,
    rentalTransactionRoute,
    rentalTransactionPopupRoute
} from './';

const ENTITY_STATES = [...rentalTransactionRoute, ...rentalTransactionPopupRoute];

@NgModule({
    imports: [LmsAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RentalTransactionComponent,
        RentalTransactionDetailComponent,
        RentalTransactionUpdateComponent,
        RentalTransactionDeleteDialogComponent,
        RentalTransactionDeletePopupComponent
    ],
    entryComponents: [
        RentalTransactionComponent,
        RentalTransactionUpdateComponent,
        RentalTransactionDeleteDialogComponent,
        RentalTransactionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LmsAppRentalTransactionModule {}
