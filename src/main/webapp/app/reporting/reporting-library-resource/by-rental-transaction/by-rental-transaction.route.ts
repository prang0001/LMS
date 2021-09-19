import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ByRentalTransactionService } from './by-rental-transaction.service';
import { ByRentalTransactionComponent } from './by-rental-transaction.component';
import { ByRentalTransactionDetailComponent } from './by-rental-transaction-detail.component';
import { ILibraryResource, LibraryResource } from 'app/shared/model/library-resource.model';

@Injectable({ providedIn: 'root' })
export class ByRentalTransactionResolve implements Resolve<ILibraryResource> {
    constructor(private service: ByRentalTransactionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .findByRentalId(id)
                .pipe(map((byRentalTransaction: HttpResponse<LibraryResource>) => byRentalTransaction.body));
        }
        return of(new LibraryResource());
    }
}

export const ByRentalTransactionRoute: Routes = [
    {
        path: 'by-rental-transaction',
        component: ByRentalTransactionComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'lmsApp.byRentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'by-rental-transaction/:id/view',
        component: ByRentalTransactionDetailComponent,
        resolve: {
            byRentalTransaction: ByRentalTransactionResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'lmsApp.byRentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
