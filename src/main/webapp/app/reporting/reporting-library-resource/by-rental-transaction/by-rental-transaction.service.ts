import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRentalTransaction, RentalTransaction } from 'app/shared/model/rental-transaction.model';
import { ILibraryResource } from 'app/shared/model/library-resource.model';
import { find, map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IRentalTransaction>;
type EntityArrayResponseType = HttpResponse<IRentalTransaction[]>;
type EntityArrayResponseType1 = HttpResponse<ILibraryResource[]>;

@Injectable({ providedIn: 'root' })
export class ByRentalTransactionService {
    public resourceUrl = SERVER_API_URL + 'api/by-rental-transactions';

    constructor(private http: HttpClient) {}

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRentalTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRentalTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    findByRentalId(id: number) {
        return this.http.get<ILibraryResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
