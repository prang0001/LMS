import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWaitingList } from 'app/shared/model/waiting-list.model';

type EntityResponseType = HttpResponse<IWaitingList>;
type EntityArrayResponseType = HttpResponse<IWaitingList[]>;

@Injectable({ providedIn: 'root' })
export class WaitingListService {
    private resourceUrl = SERVER_API_URL + 'api/waiting-lists';

    constructor(private http: HttpClient) {}

    create(waitingList: IWaitingList): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(waitingList);
        return this.http
            .post<IWaitingList>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(waitingList: IWaitingList): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(waitingList);
        return this.http
            .put<IWaitingList>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWaitingList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWaitingList[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(waitingList: IWaitingList): IWaitingList {
        const copy: IWaitingList = Object.assign({}, waitingList, {
            dateRequest:
                waitingList.dateRequest != null && waitingList.dateRequest.isValid() ? waitingList.dateRequest.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateRequest = res.body.dateRequest != null ? moment(res.body.dateRequest) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((waitingList: IWaitingList) => {
            waitingList.dateRequest = waitingList.dateRequest != null ? moment(waitingList.dateRequest) : null;
        });
        return res;
    }
}
