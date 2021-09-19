import { Moment } from 'moment';
import { ILibraryResource } from 'app/shared/model//library-resource.model';
import { IPatron } from 'app/shared/model//patron.model';

export interface IRentalTransaction {
    id?: number;
    rentalPeriod?: number;
    rentalStartDate?: Moment;
    rentalDueDate?: Moment;
    extendRental?: boolean;
    rentalFinalDueDate?: Moment;
    overdue?: boolean;
    daysOverdue?: number;
    finesOverdue?: number;
    libraryResources?: ILibraryResource[];
    patrons?: IPatron[];
}

export class RentalTransaction implements IRentalTransaction {
    constructor(
        public id?: number,
        public rentalPeriod?: number,
        public rentalStartDate?: Moment,
        public rentalDueDate?: Moment,
        public extendRental?: boolean,
        public rentalFinalDueDate?: Moment,
        public overdue?: boolean,
        public daysOverdue?: number,
        public finesOverdue?: number,
        public libraryResources?: ILibraryResource[],
        public patrons?: IPatron[]
    ) {
        this.extendRental = this.extendRental || false;
        this.overdue = this.overdue || false;
    }
}
