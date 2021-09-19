import { IRentalTransaction } from 'app/shared/model//rental-transaction.model';
import { IWaitingList } from 'app/shared/model//waiting-list.model';

export interface IPatron {
    id?: number;
    patronStatus?: string;
    firstName?: string;
    middleName?: string;
    lastName?: string;
    streetAddress?: string;
    mailingAddress?: string;
    email?: string;
    phoneNum1?: string;
    phoneNum2?: string;
    phoneNum3?: string;
    login?: string;
    rentalTransactions?: IRentalTransaction[];
    waitingLists?: IWaitingList[];
}

export class Patron implements IPatron {
    constructor(
        public id?: number,
        public patronStatus?: string,
        public firstName?: string,
        public middleName?: string,
        public lastName?: string,
        public streetAddress?: string,
        public mailingAddress?: string,
        public email?: string,
        public phoneNum1?: string,
        public phoneNum2?: string,
        public phoneNum3?: string,
        public login?: string,
        public rentalTransactions?: IRentalTransaction[],
        public waitingLists?: IWaitingList[]
    ) {}
}
