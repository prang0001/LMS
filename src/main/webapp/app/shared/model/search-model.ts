export interface ISearch {
    searchData?: string;
    searchType?: string;
    author?: string;
    title?: string;
    callNumber?: number;
}

export class SearchRequest implements ISearch {
    constructor(
        public searchData?: string,
        public searchType?: string,
        public author?: string,
        public id?: number,
        public title?: string,
        public callNumber?: number
    ) {}
}
