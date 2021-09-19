import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../app/app.constants';
import { createRequestOption } from '../../../app/shared';
import { ILibraryResource } from '../../../app/shared/model/library-resource.model';
import { SearchRequest } from '../../../app/shared/model/search-model';
import { ISearchResponse } from '../../../app/shared/model/search-response-model';

type EntityResponseType = HttpResponse<ISearchResponse>;
type EntityArrayResponseType = HttpResponse<ISearchResponse[]>;

@Injectable({ providedIn: 'root' })
export class SearchService {
    private resourceUrl = SERVER_API_URL + 'api/search';

    constructor(private http: HttpClient) {}

    findResource(searchData: string, searchType: string): Observable<EntityResponseType> {
        console.log('Attempting to find resource...');
        searchType = searchType.replace(/%20/g, ' ');
        const searchReq = {
            searchType: searchType,
            id: searchType === 'Resource ID' ? parseInt(searchData) : null,
            title: searchType === 'Title' ? searchData : null,
            callNumber: searchType === 'Call Number' ? parseInt(searchData) : null,
            author: searchType === 'Author' ? searchData : null,
            description: searchType === 'Description' ? searchData : null,
            subject: searchType === 'Subject' ? searchData : null
        };
        console.log('Request object:' + JSON.stringify(searchReq));
        return this.http.post<ISearchResponse>(this.resourceUrl, searchReq, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISearchResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
