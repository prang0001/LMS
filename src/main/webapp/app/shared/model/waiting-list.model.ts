import { Moment } from 'moment';
import { ILibraryResource } from 'app/shared/model//library-resource.model';
import { IPatron } from 'app/shared/model//patron.model';

export interface IWaitingList {
    id?: number;
    dateRequest?: Moment;
    requested?: boolean;
    libraryResources?: ILibraryResource[];
    patrons?: IPatron[];
}

export class WaitingList implements IWaitingList {
    constructor(
        public id?: number,
        public dateRequest?: Moment,
        public requested?: boolean,
        public libraryResources?: ILibraryResource[],
        public patrons?: IPatron[]
    ) {
        this.requested = this.requested || false;
    }
}
