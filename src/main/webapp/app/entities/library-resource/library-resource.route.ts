import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LibraryResource } from 'app/shared/model/library-resource.model';
import { LibraryResourceService } from './library-resource.service';
import { LibraryResourceComponent } from './library-resource.component';
import { LibraryResourceDetailComponent } from './library-resource-detail.component';
import { LibraryResourceUpdateComponent } from './library-resource-update.component';
import { LibraryResourceDeletePopupComponent } from './library-resource-delete-dialog.component';
import { ILibraryResource } from 'app/shared/model/library-resource.model';

@Injectable({ providedIn: 'root' })
export class LibraryResourceResolve implements Resolve<ILibraryResource> {
    constructor(private service: LibraryResourceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((libraryResource: HttpResponse<LibraryResource>) => libraryResource.body));
        }
        return of(new LibraryResource());
    }
}

export const libraryResourceRoute: Routes = [
    {
        path: 'library-resource',
        component: LibraryResourceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.libraryResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'library-resource/:id/view',
        component: LibraryResourceDetailComponent,
        resolve: {
            libraryResource: LibraryResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.libraryResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'library-resource/new',
        component: LibraryResourceUpdateComponent,
        resolve: {
            libraryResource: LibraryResourceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'lmsApp.libraryResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'library-resource/:id/edit',
        component: LibraryResourceUpdateComponent,
        resolve: {
            libraryResource: LibraryResourceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'lmsApp.libraryResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const libraryResourcePopupRoute: Routes = [
    {
        path: 'library-resource/:id/delete',
        component: LibraryResourceDeletePopupComponent,
        resolve: {
            libraryResource: LibraryResourceResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'lmsApp.libraryResource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
