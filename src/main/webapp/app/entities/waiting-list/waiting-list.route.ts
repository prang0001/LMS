import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WaitingList } from 'app/shared/model/waiting-list.model';
import { WaitingListService } from './waiting-list.service';
import { WaitingListComponent } from './waiting-list.component';
import { WaitingListDetailComponent } from './waiting-list-detail.component';
import { WaitingListUpdateComponent } from './waiting-list-update.component';
import { WaitingListDeletePopupComponent } from './waiting-list-delete-dialog.component';
import { IWaitingList } from 'app/shared/model/waiting-list.model';

@Injectable({ providedIn: 'root' })
export class WaitingListResolve implements Resolve<IWaitingList> {
    constructor(private service: WaitingListService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((waitingList: HttpResponse<WaitingList>) => waitingList.body));
        }
        return of(new WaitingList());
    }
}

export const waitingListRoute: Routes = [
    {
        path: 'waiting-list',
        component: WaitingListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.waitingList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'waiting-list/:id/view',
        component: WaitingListDetailComponent,
        resolve: {
            waitingList: WaitingListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.waitingList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'waiting-list/new',
        component: WaitingListUpdateComponent,
        resolve: {
            waitingList: WaitingListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.waitingList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'waiting-list/:id/edit',
        component: WaitingListUpdateComponent,
        resolve: {
            waitingList: WaitingListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.waitingList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const waitingListPopupRoute: Routes = [
    {
        path: 'waiting-list/:id/delete',
        component: WaitingListDeletePopupComponent,
        resolve: {
            waitingList: WaitingListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.waitingList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
