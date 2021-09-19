import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WaitingList } from 'app/shared/model/waiting-list.model';
import { ByWaitingListService } from './by-waiting-list.service';
import { ByWaitingListComponent } from './by-waiting-list.component';
import { ByWaitingListDetailComponent } from './by-waiting-list-detail.component';
import { IWaitingList } from 'app/shared/model/waiting-list.model';
import { ILibraryResource, LibraryResource } from 'app/shared/model/library-resource.model';

@Injectable({ providedIn: 'root' })
export class ByWaitingListResolve implements Resolve<ILibraryResource> {
    constructor(private service: ByWaitingListService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.findByWaitingListId(id).pipe(map((byWaitingList: HttpResponse<LibraryResource>) => byWaitingList.body));
        }
        return of(new LibraryResource());
    }
}

export const ByWaitingListRoute: Routes = [
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
        path: 'by-waiting-list/:id/view',
        component: ByWaitingListDetailComponent,
        resolve: {
            byWaitingList: ByWaitingListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lmsApp.byWaitingList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
