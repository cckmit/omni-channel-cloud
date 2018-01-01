import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OperationLog } from './operation-log.model';
import { OperationLogPopupService } from './operation-log-popup.service';
import { OperationLogService } from './operation-log.service';
import { OperationType, OperationTypeService } from '../operation-type';
import { Inventory, InventoryService } from '../inventory';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-operation-log-dialog',
    templateUrl: './operation-log-dialog.component.html'
})
export class OperationLogDialogComponent implements OnInit {

    operationLog: OperationLog;
    isSaving: boolean;

    operationtypes: OperationType[];

    inventories: Inventory[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private operationLogService: OperationLogService,
        private operationTypeService: OperationTypeService,
        private inventoryService: InventoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.operationTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.operationtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.inventoryService.query()
            .subscribe((res: ResponseWrapper) => { this.inventories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operationLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operationLogService.update(this.operationLog));
        } else {
            this.subscribeToSaveResponse(
                this.operationLogService.create(this.operationLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<OperationLog>) {
        result.subscribe((res: OperationLog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OperationLog) {
        this.eventManager.broadcast({ name: 'operationLogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOperationTypeById(index: number, item: OperationType) {
        return item.id;
    }

    trackInventoryById(index: number, item: Inventory) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-operation-log-popup',
    template: ''
})
export class OperationLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationLogPopupService: OperationLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.operationLogPopupService
                    .open(OperationLogDialogComponent as Component, params['id']);
            } else {
                this.operationLogPopupService
                    .open(OperationLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
