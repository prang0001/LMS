import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResourceType } from 'app/shared/model/resource-type.model';
import { ByResourceTypeService } from './by-resource-type.service';
import { ByResourceTypeComponent } from './by-resource-type.component';
import { ByResourceTypeDetailComponent } from './by-resource-type-detail.component';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { ILibraryResource, LibraryResource } from 'app/shared/model/library-resource.model';

@Injectable({ providedIn: 'root' })
export class ByResourceTypeResolve implements Resolve<ILibraryResource> {
    constructor(private service: ByResourceTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.findByTypeId(id).pipe(map((byType: HttpResponse<LibraryResource>) => byType.body));
        }
        return of(new LibraryResource());
    }
}

export const ByResourceTypeRoute: Routes = [
    {
        path: 'by-resource-type',
        component: ByResourceTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byResourceType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'by-resource-type/:id/view',
        component: ByResourceTypeDetailComponent,
        resolve: {
            byResourceType: ByResourceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byResourceType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
