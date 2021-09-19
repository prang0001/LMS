import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Patron } from 'app/shared/model/patron.model';
import { PatronService } from './patron.service';
import { PatronComponent } from './patron.component';
import { PatronDetailComponent } from './patron-detail.component';
import { PatronUpdateComponent } from './patron-update.component';
import { PatronDeletePopupComponent } from './patron-delete-dialog.component';
import { IPatron } from 'app/shared/model/patron.model';

@Injectable({ providedIn: 'root' })
export class PatronResolve implements Resolve<IPatron> {
    constructor(private service: PatronService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((patron: HttpResponse<Patron>) => patron.body));
        }
        return of(new Patron());
    }
}

export const patronRoute: Routes = [
    {
        path: 'patron',
        component: PatronComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.patron.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patron/:id/view',
        component: PatronDetailComponent,
        resolve: {
            patron: PatronResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.patron.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patron/new',
        component: PatronUpdateComponent,
        resolve: {
            patron: PatronResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.patron.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patron/:id/edit',
        component: PatronUpdateComponent,
        resolve: {
            patron: PatronResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.patron.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patronPopupRoute: Routes = [
    {
        path: 'patron/:id/delete',
        component: PatronDeletePopupComponent,
        resolve: {
            patron: PatronResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.patron.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
