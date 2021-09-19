import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from '../../core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchResultsComponent } from './search-results.component';
import { SearchResponse, ISearchResponse } from '../../../app/shared/model/search-response-model';
import { SearchService } from './search.service';

@Injectable({ providedIn: 'root' })
export class SearchResultsRoute implements Resolve<ISearchResponse> {
    constructor(private searchService: SearchService) {}
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const searchData = route.params['searchData'] ? route.params['searchData'] : null;
        const searchType = route.params['searchType'] ? route.params['searchType'] : null;
        console.log('sdata' + searchData);
        console.log('stype' + searchType);
        if (searchData && searchType) {
            return this.searchService
                .findResource(searchData, searchType)
                .pipe(map((searchResponse: HttpResponse<SearchResponse>) => searchResponse.body));
        }
        return of(new SearchResponse());
    }
}
export const searchResultsRoute: Routes = [
    {
        path: 'search',
        component: SearchResultsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'global.title'
        },
        canActivate: []
    },
    {
        path: 'search/:searchData/:searchType',
        component: SearchResultsComponent,
        resolve: {
            searchResponse: SearchResultsRoute
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'global.title'
        },
        canActivate: []
    }
];
