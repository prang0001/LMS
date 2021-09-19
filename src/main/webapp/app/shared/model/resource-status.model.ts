import { ILibraryResource } from 'app/shared/model//library-resource.model';

export interface IResourceStatus {
    id?: number;
    statusName?: string;
    libraryResources?: ILibraryResource[];
}

export class ResourceStatus implements IResourceStatus {
    constructor(public id?: number, public statusName?: string, public libraryResources?: ILibraryResource[]) {}
}
