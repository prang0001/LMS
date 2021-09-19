import { ILibraryResource } from 'app/shared/model//library-resource.model';

export interface IAuthor {
    id?: number;
    authorName?: string;
    libraryResources?: ILibraryResource[];
}

export class Author implements IAuthor {
    constructor(public id?: number, public authorName?: string, public libraryResources?: ILibraryResource[]) {}
}
