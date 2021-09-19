/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RentalTransactionService } from 'app/entities/rental-transaction/rental-transaction.service';
import { IRentalTransaction, RentalTransaction } from 'app/shared/model/rental-transaction.model';

describe('Service Tests', () => {
    describe('RentalTransaction Service', () => {
        let injector: TestBed;
        let service: RentalTransactionService;
        let httpMock: HttpTestingController;
        let elemDefault: IRentalTransaction;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RentalTransactionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new RentalTransaction(0, 0, currentDate, currentDate, false, currentDate, false, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        rentalStartDate: currentDate.format(DATE_FORMAT),
                        rentalDueDate: currentDate.format(DATE_FORMAT),
                        rentalFinalDueDate: currentDate.format(DATE_FORMAT)
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

            it('should create a RentalTransaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        rentalStartDate: currentDate.format(DATE_FORMAT),
                        rentalDueDate: currentDate.format(DATE_FORMAT),
                        rentalFinalDueDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        rentalStartDate: currentDate,
                        rentalDueDate: currentDate,
                        rentalFinalDueDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new RentalTransaction(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a RentalTransaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        rentalPeriod: 1,
                        rentalStartDate: currentDate.format(DATE_FORMAT),
                        rentalDueDate: currentDate.format(DATE_FORMAT),
                        extendRental: true,
                        rentalFinalDueDate: currentDate.format(DATE_FORMAT),
                        overdue: true,
                        daysOverdue: 1,
                        finesOverdue: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        rentalStartDate: currentDate,
                        rentalDueDate: currentDate,
                        rentalFinalDueDate: currentDate
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

            it('should return a list of RentalTransaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        rentalPeriod: 1,
                        rentalStartDate: currentDate.format(DATE_FORMAT),
                        rentalDueDate: currentDate.format(DATE_FORMAT),
                        extendRental: true,
                        rentalFinalDueDate: currentDate.format(DATE_FORMAT),
                        overdue: true,
                        daysOverdue: 1,
                        finesOverdue: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        rentalStartDate: currentDate,
                        rentalDueDate: currentDate,
                        rentalFinalDueDate: currentDate
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

            it('should delete a RentalTransaction', async () => {
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
