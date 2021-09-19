import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { MyRentalsComponent } from './my-rentals.component';

export const myRentalsRoute: Route = {
    path: 'myrentals',
    component: MyRentalsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
