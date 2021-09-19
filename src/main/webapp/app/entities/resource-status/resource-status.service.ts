import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResourceStatus } from 'app/shared/model/resource-status.model';

type EntityResponseType = HttpResponse<IResourceStatus>;
type EntityArrayResponseType = HttpResponse<IResourceStatus[]>;

@Injectable({ providedIn: 'root' })
export class ResourceStatusService {
    private resourceUrl = SERVER_API_URL + 'api/resource-statuses';

    constructor(private http: HttpClient) {}

    create(resourceStatus: IResourceStatus): Observable<EntityResponseType> {
        return this.http.post<IResourceStatus>(this.resourceUrl, resourceStatus, { observe: 'response' });
    }

    update(resourceStatus: IResourceStatus): Observable<EntityResponseType> {
        return this.http.put<IResourceStatus>(this.resourceUrl, resourceStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResourceStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResourceStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
