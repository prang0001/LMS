import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatron } from 'app/shared/model/patron.model';

@Component({
    selector: 'jhi-patron-detail',
    templateUrl: './patron-detail.component.html'
})
export class PatronDetailComponent implements OnInit {
    patron: IPatron;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ patron }) => {
            this.patron = patron;
        });
    }

    previousState() {
        window.history.back();
    }
}
