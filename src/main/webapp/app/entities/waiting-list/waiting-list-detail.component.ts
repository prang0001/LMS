import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWaitingList } from 'app/shared/model/waiting-list.model';

@Component({
    selector: 'jhi-waiting-list-detail',
    templateUrl: './waiting-list-detail.component.html'
})
export class WaitingListDetailComponent implements OnInit {
    waitingList: IWaitingList;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ waitingList }) => {
            this.waitingList = waitingList;
        });
    }

    previousState() {
        window.history.back();
    }
}
