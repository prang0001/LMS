import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from '../../core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReportingLibraryResourceComponent } from './reporting-library-resource.component';
import { ByStatusComponent } from './by-status/by-status.component';
import { ByWaitingListComponent } from 'app/reporting/reporting-library-resource/by-waiting-list';
import { ByRentalTransactionComponent } from 'app/reporting/reporting-library-resource/by-rental-transaction';
import { ByResourceTypeComponent } from 'app/reporting/reporting-library-resource/by-resource-type';

@Injectable({ providedIn: 'root' })
export class ReportingLibraryResourceRoute {
    constructor() {}
}

export const reportingLibraryResourceRoute: Routes = [
    {
        path: 'reporting-library-resource',
        component: ReportingLibraryResourceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'global.menu.reporting.main'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'by-status',
        component: ByStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'by-waiting-list',
        component: ByWaitingListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byWaitingList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'by-rental-transaction',
        component: ByRentalTransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byRentalTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'by-resource-type',
        component: ByResourceTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byResourceType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
