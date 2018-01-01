import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LockLog } from './lock-log.model';
import { LockLogService } from './lock-log.service';

@Component({
    selector: 'jhi-lock-log-detail',
    templateUrl: './lock-log-detail.component.html'
})
export class LockLogDetailComponent implements OnInit, OnDestroy {

    lockLog: LockLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lockLogService: LockLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLockLogs();
    }

    load(id) {
        this.lockLogService.find(id).subscribe((lockLog) => {
            this.lockLog = lockLog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLockLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lockLogListModification',
            (response) => this.load(this.lockLog.id)
        );
    }
}
