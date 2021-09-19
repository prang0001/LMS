export interface ISearchResponse {
    statusCode?: string;
    statusMessage?: string;
    searchResults?: Array<Object>;
}

export class SearchResponse implements ISearchResponse {
    constructor(public statusCode?: string, public statusMessage?: string, public searchResults?: Array<Object>) {}
}
