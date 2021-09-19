import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWaitingList, WaitingList } from 'app/shared/model/waiting-list.model';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { find, map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IWaitingList>;
type EntityArrayResponseType = HttpResponse<IWaitingList[]>;
type EntityArrayResponseType1 = HttpResponse<ILibraryResource[]>;

@Injectable({ providedIn: 'root' })
export class ByWaitingListService {
    public resourceUrl = SERVER_API_URL + 'api/by-waiting-lists';

    constructor(private http: HttpClient) {}

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWaitingList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWaitingList[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    findByWaitingListId(id: number) {
        return this.http.get<ILibraryResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
