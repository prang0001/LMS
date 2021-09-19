import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRentalTransaction } from 'app/shared/model/rental-transaction.model';

type EntityResponseType = HttpResponse<IRentalTransaction>;
type EntityArrayResponseType = HttpResponse<IRentalTransaction[]>;

@Injectable({ providedIn: 'root' })
export class RentalTransactionService {
    private resourceUrl = SERVER_API_URL + 'api/rental-transactions';
    private resourceUrlForUser = SERVER_API_URL + 'api/rental-transactions-for-user';

    constructor(private http: HttpClient) {}

    create(rentalTransaction: IRentalTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rentalTransaction);
        return this.http
            .post<IRentalTransaction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rentalTransaction: IRentalTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rentalTransaction);
        return this.http
            .put<IRentalTransaction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRentalTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByLogin(login: string): Observable<EntityResponseType> {
        return this.http
            .get<IRentalTransaction>(`${this.resourceUrlForUser}?login=${login}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRentalTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(rentalTransaction: IRentalTransaction): IRentalTransaction {
        const copy: IRentalTransaction = Object.assign({}, rentalTransaction, {
            rentalStartDate:
                rentalTransaction.rentalStartDate != null && rentalTransaction.rentalStartDate.isValid()
                    ? rentalTransaction.rentalStartDate.format(DATE_FORMAT)
                    : null,
            rentalDueDate:
                rentalTransaction.rentalDueDate != null && rentalTransaction.rentalDueDate.isValid()
                    ? rentalTransaction.rentalDueDate.format(DATE_FORMAT)
                    : null,
            rentalFinalDueDate:
                rentalTransaction.rentalFinalDueDate != null && rentalTransaction.rentalFinalDueDate.isValid()
                    ? rentalTransaction.rentalFinalDueDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.rentalStartDate = res.body.rentalStartDate != null ? moment(res.body.rentalStartDate) : null;
        res.body.rentalDueDate = res.body.rentalDueDate != null ? moment(res.body.rentalDueDate) : null;
        res.body.rentalFinalDueDate = res.body.rentalFinalDueDate != null ? moment(res.body.rentalFinalDueDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((rentalTransaction: IRentalTransaction) => {
            rentalTransaction.rentalStartDate =
                rentalTransaction.rentalStartDate != null ? moment(rentalTransaction.rentalStartDate) : null;
            rentalTransaction.rentalDueDate = rentalTransaction.rentalDueDate != null ? moment(rentalTransaction.rentalDueDate) : null;
            rentalTransaction.rentalFinalDueDate =
                rentalTransaction.rentalFinalDueDate != null ? moment(rentalTransaction.rentalFinalDueDate) : null;
        });
        return res;
    }
}
