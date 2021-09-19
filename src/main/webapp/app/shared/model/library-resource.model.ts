import { IAuthor } from 'app/shared/model//author.model';
import { ISubject } from 'app/shared/model//subject.model';
import { IResourceStatus } from 'app/shared/model//resource-status.model';
import { IResourceType } from 'app/shared/model//resource-type.model';
import { IRentalTransaction } from 'app/shared/model//rental-transaction.model';
import { IWaitingList } from 'app/shared/model//waiting-list.model';

export interface ILibraryResource {
    id?: number;
    resourceTitle?: string;
    resourceDescription?: string;
    callNumber?: string;
    author?: IAuthor;
    subject?: ISubject;
    resourceStatus?: IResourceStatus;
    resourceType?: IResourceType;
    rentalTransactions?: IRentalTransaction[];
    waitingLists?: IWaitingList[];
}

export class LibraryResource implements ILibraryResource {
    constructor(
        public id?: number,
        public resourceTitle?: string,
        public resourceDescription?: string,
        public callNumber?: string,
        public author?: IAuthor,
        public subject?: ISubject,
        public resourceStatus?: IResourceStatus,
        public resourceType?: IResourceType,
        public rentalTransactions?: IRentalTransaction[],
        public waitingLists?: IWaitingList[]
    ) {}
}
