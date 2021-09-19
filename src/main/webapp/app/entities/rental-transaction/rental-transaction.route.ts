import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RentalTransaction } from 'app/shared/model/rental-transaction.model';
import { RentalTransactionService } from './rental-transaction.service';
import { RentalTransactionComponent } from './rental-transaction.component';
import { RentalTransactionDetailComponent } from './rental-transaction-detail.component';
import { RentalTransactionUpdateComponent } from './rental-transaction-update.component';
import { RentalTransactionDeletePopupComponent } from './rental-transaction-delete-dialog.component';
import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';

@Injectable({ providedIn: 'root' })
export class RentalTransactionResolve implements Resolve<IRentalTransaction> {
    constructor(private service: RentalTransactionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((rentalTransaction: HttpResponse<RentalTransaction>) => rentalTransaction.body));
        }
        return of(new RentalTransaction());
    }
}

export const rentalTransactionRoute: Routes = [
    {
        path: 'rental-transaction',
        component: RentalTransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.rentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rental-transaction/:id/view',
        component: RentalTransactionDetailComponent,
        resolve: {
            rentalTransaction: RentalTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.rentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rental-transaction/new',
        component: RentalTransactionUpdateComponent,
        resolve: {
            rentalTransaction: RentalTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.rentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rental-transaction/:id/edit',
        component: RentalTransactionUpdateComponent,
        resolve: {
            rentalTransaction: RentalTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.rentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rentalTransactionPopupRoute: Routes = [
    {
        path: 'rental-transaction/:id/delete',
        component: RentalTransactionDeletePopupComponent,
        resolve: {
            rentalTransaction: RentalTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.rentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
