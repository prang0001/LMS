import { ILibraryResource } from 'app/shared/model//library-resource.model';

export interface IResourceType {
    id?: number;
    resourceTypeName?: string;
    rentalDuration?: number;
    overdueCharge?: number;
    libraryResources?: ILibraryResource[];
}

export class ResourceType implements IResourceType {
    constructor(
        public id?: number,
        public resourceTypeName?: string,
        public rentalDuration?: number,
        public overdueCharge?: number,
        public libraryResources?: ILibraryResource[]
    ) {}
}
