import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OperationType } from './operation-type.model';
import { OperationTypePopupService } from './operation-type-popup.service';
import { OperationTypeService } from './operation-type.service';

@Component({
    selector: 'jhi-operation-type-dialog',
    templateUrl: './operation-type-dialog.component.html'
})
export class OperationTypeDialogComponent implements OnInit {

    operationType: OperationType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private operationTypeService: OperationTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operationType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operationTypeService.update(this.operationType));
        } else {
            this.subscribeToSaveResponse(
                this.operationTypeService.create(this.operationType));
        }
    }

    private subscribeToSaveResponse(result: Observable<OperationType>) {
        result.subscribe((res: OperationType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OperationType) {
        this.eventManager.broadcast({ name: 'operationTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-operation-type-popup',
    template: ''
})
export class OperationTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationTypePopupService: OperationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.operationTypePopupService
                    .open(OperationTypeDialogComponent as Component, params['id']);
            } else {
                this.operationTypePopupService
                    .open(OperationTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
