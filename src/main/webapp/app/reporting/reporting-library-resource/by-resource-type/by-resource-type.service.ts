import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResourceType, ResourceType } from 'app/shared/model/resource-type.model';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { find, map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IResourceType>;
type EntityArrayResponseType = HttpResponse<IResourceType[]>;
type EntityArrayResponseType1 = HttpResponse<ILibraryResource[]>;

@Injectable({ providedIn: 'root' })
export class ByResourceTypeService {
    public resourceUrl = SERVER_API_URL + 'api/by-resource-types';

    constructor(private http: HttpClient) {}

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResourceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResourceType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    findByTypeId(id: number) {
        return this.http.get<ILibraryResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
