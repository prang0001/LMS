import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILibraryResource } from 'app/shared/model/library-resource.model';

type EntityResponseType = HttpResponse<ILibraryResource>;
type EntityArrayResponseType = HttpResponse<ILibraryResource[]>;

@Injectable({ providedIn: 'root' })
export class LibraryResourceService {
    private resourceUrl = SERVER_API_URL + 'api/library-resources';

    constructor(private http: HttpClient) {}

    create(libraryResource: ILibraryResource): Observable<EntityResponseType> {
        return this.http.post<ILibraryResource>(this.resourceUrl, libraryResource, { observe: 'response' });
    }

    update(libraryResource: ILibraryResource): Observable<EntityResponseType> {
        return this.http.put<ILibraryResource>(this.resourceUrl, libraryResource, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILibraryResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILibraryResource[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
