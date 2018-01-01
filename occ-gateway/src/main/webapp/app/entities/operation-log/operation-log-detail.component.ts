import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OperationLog } from './operation-log.model';
import { OperationLogService } from './operation-log.service';

@Component({
    selector: 'jhi-operation-log-detail',
    templateUrl: './operation-log-detail.component.html'
})
export class OperationLogDetailComponent implements OnInit, OnDestroy {

    operationLog: OperationLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private operationLogService: OperationLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOperationLogs();
    }

    load(id) {
        this.operationLogService.find(id).subscribe((operationLog) => {
            this.operationLog = operationLog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOperationLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'operationLogListModification',
            (response) => this.load(this.operationLog.id)
        );
    }
}
