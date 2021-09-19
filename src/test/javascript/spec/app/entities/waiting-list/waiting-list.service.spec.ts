/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { WaitingListService } from 'app/entities/waiting-list/waiting-list.service';
import { IWaitingList, WaitingList } from 'app/shared/model/waiting-list.model';

describe('Service Tests', () => {
    describe('WaitingList Service', () => {
        let injector: TestBed;
        let service: WaitingListService;
        let httpMock: HttpTestingController;
        let elemDefault: IWaitingList;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(WaitingListService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new WaitingList(0, currentDate, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateRequest: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a WaitingList', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateRequest: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateRequest: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new WaitingList(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a WaitingList', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateRequest: currentDate.format(DATE_FORMAT),
                        requested: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateRequest: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of WaitingList', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateRequest: currentDate.format(DATE_FORMAT),
                        requested: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateRequest: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a WaitingList', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
