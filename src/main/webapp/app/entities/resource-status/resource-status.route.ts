import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResourceStatus } from 'app/shared/model/resource-status.model';
import { ResourceStatusService } from './resource-status.service';
import { ResourceStatusComponent } from './resource-status.component';
import { ResourceStatusDetailComponent } from './resource-status-detail.component';
import { ResourceStatusUpdateComponent } from './resource-status-update.component';
import { ResourceStatusDeletePopupComponent } from './resource-status-delete-dialog.component';
import { IResourceStatus } from 'app/shared/model/resource-status.model';

@Injectable({ providedIn: 'root' })
export class ResourceStatusResolve implements Resolve<IResourceStatus> {
    constructor(private service: ResourceStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((resourceStatus: HttpResponse<ResourceStatus>) => resourceStatus.body));
        }
        return of(new ResourceStatus());
    }
}

export const resourceStatusRoute: Routes = [
    {
        path: 'resource-status',
        component: ResourceStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.resourceStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resource-status/:id/view',
        component: ResourceStatusDetailComponent,
        resolve: {
            resourceStatus: ResourceStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.resourceStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resource-status/new',
        component: ResourceStatusUpdateComponent,
        resolve: {
            resourceStatus: ResourceStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.resourceStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resource-status/:id/edit',
        component: ResourceStatusUpdateComponent,
        resolve: {
            resourceStatus: ResourceStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.resourceStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resourceStatusPopupRoute: Routes = [
    {
        path: 'resource-status/:id/delete',
        component: ResourceStatusDeletePopupComponent,
        resolve: {
            resourceStatus: ResourceStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.resourceStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
