import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LockLog } from './lock-log.model';
import { LockLogPopupService } from './lock-log-popup.service';
import { LockLogService } from './lock-log.service';
import { Inventory, InventoryService } from '../inventory';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lock-log-dialog',
    templateUrl: './lock-log-dialog.component.html'
})
export class LockLogDialogComponent implements OnInit {

    lockLog: LockLog;
    isSaving: boolean;

    inventories: Inventory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lockLogService: LockLogService,
        private inventoryService: InventoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventoryService.query()
            .subscribe((res: ResponseWrapper) => { this.inventories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lockLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lockLogService.update(this.lockLog));
        } else {
            this.subscribeToSaveResponse(
                this.lockLogService.create(this.lockLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<LockLog>) {
        result.subscribe((res: LockLog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LockLog) {
        this.eventManager.broadcast({ name: 'lockLogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInventoryById(index: number, item: Inventory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-lock-log-popup',
    template: ''
})
export class LockLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lockLogPopupService: LockLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lockLogPopupService
                    .open(LockLogDialogComponent as Component, params['id']);
            } else {
                this.lockLogPopupService
                    .open(LockLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
