import { ILibraryResource } from 'app/shared/model//library-resource.model';

export interface ISubject {
    id?: number;
    subjectName?: string;
    libraryResources?: ILibraryResource[];
}

export class Subject implements ISubject {
    constructor(public id?: number, public subjectName?: string, public libraryResources?: ILibraryResource[]) {}
}
