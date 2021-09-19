import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResourceStatus } from 'app/shared/model/resource-status.model';
import { ByStatusService } from './by-status.service';
import { ByStatusComponent } from './by-status.component';
import { ByStatusDetailComponent } from './by-status-detail.component';
import { IResourceStatus } from 'app/shared/model/resource-status.model';
import { ILibraryResource, LibraryResource } from 'app/shared/model/library-resource.model';

@Injectable({ providedIn: 'root' })
export class ByStatusResolve implements Resolve<ILibraryResource> {
    constructor(private service: ByStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.findByStatus(id).pipe(map((byStatus: HttpResponse<LibraryResource>) => byStatus.body));
        }
        return of(new LibraryResource());
    }
}

export const ByStatusRoute: Routes = [
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
        path: 'by-status/:id/view',
        component: ByStatusDetailComponent,
        resolve: {
            byStatus: ByStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
