import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPatron } from 'app/shared/model/patron.model';

type EntityResponseType = HttpResponse<IPatron>;
type EntityArrayResponseType = HttpResponse<IPatron[]>;

@Injectable({ providedIn: 'root' })
export class PatronService {
    private resourceUrl = SERVER_API_URL + 'api/patrons';

    constructor(private http: HttpClient) {}

    create(patron: IPatron): Observable<EntityResponseType> {
        console.log('Creating new patron...');
        console.log(JSON.stringify(patron));
        return this.http.post<IPatron>(this.resourceUrl, patron, { observe: 'response' });
    }

    update(patron: IPatron): Observable<EntityResponseType> {
        return this.http.put<IPatron>(this.resourceUrl, patron, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPatron>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPatron[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
