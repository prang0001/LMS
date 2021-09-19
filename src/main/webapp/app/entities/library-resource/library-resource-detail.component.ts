import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILibraryResource } from 'app/shared/model/library-resource.model';

@Component({
    selector: 'jhi-library-resource-detail',
    templateUrl: './library-resource-detail.component.html'
})
export class LibraryResourceDetailComponent implements OnInit {
    libraryResource: ILibraryResource;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ libraryResource }) => {
            this.libraryResource = libraryResource;
        });
    }

    previousState() {
        window.history.back();
    }
}
