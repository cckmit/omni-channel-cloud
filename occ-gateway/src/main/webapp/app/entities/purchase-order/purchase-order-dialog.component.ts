import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PurchaseOrder } from './purchase-order.model';
import { PurchaseOrderPopupService } from './purchase-order-popup.service';
import { PurchaseOrderService } from './purchase-order.service';
import { PoType, PoTypeService } from '../po-type';
import { PoState, PoStateService } from '../po-state';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-purchase-order-dialog',
    templateUrl: './purchase-order-dialog.component.html'
})
export class PurchaseOrderDialogComponent implements OnInit {

    purchaseOrder: PurchaseOrder;
    isSaving: boolean;

    potypes: PoType[];

    postates: PoState[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private purchaseOrderService: PurchaseOrderService,
        private poTypeService: PoTypeService,
        private poStateService: PoStateService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.poTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.potypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.poStateService.query()
            .subscribe((res: ResponseWrapper) => { this.postates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.purchaseOrderService.update(this.purchaseOrder));
        } else {
            this.subscribeToSaveResponse(
                this.purchaseOrderService.create(this.purchaseOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<PurchaseOrder>) {
        result.subscribe((res: PurchaseOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PurchaseOrder) {
        this.eventManager.broadcast({ name: 'purchaseOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPoTypeById(index: number, item: PoType) {
        return item.id;
    }

    trackPoStateById(index: number, item: PoState) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-purchase-order-popup',
    template: ''
})
export class PurchaseOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private purchaseOrderPopupService: PurchaseOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.purchaseOrderPopupService
                    .open(PurchaseOrderDialogComponent as Component, params['id']);
            } else {
                this.purchaseOrderPopupService
                    .open(PurchaseOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
