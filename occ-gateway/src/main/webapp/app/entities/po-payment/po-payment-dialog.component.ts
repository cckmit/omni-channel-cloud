import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PoPayment } from './po-payment.model';
import { PoPaymentPopupService } from './po-payment-popup.service';
import { PoPaymentService } from './po-payment.service';
import { PurchaseOrder, PurchaseOrderService } from '../purchase-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-po-payment-dialog',
    templateUrl: './po-payment-dialog.component.html'
})
export class PoPaymentDialogComponent implements OnInit {

    poPayment: PoPayment;
    isSaving: boolean;

    purchaseorders: PurchaseOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private poPaymentService: PoPaymentService,
        private purchaseOrderService: PurchaseOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.purchaseOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.purchaseorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.poPayment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.poPaymentService.update(this.poPayment));
        } else {
            this.subscribeToSaveResponse(
                this.poPaymentService.create(this.poPayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<PoPayment>) {
        result.subscribe((res: PoPayment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PoPayment) {
        this.eventManager.broadcast({ name: 'poPaymentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPurchaseOrderById(index: number, item: PurchaseOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-po-payment-popup',
    template: ''
})
export class PoPaymentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poPaymentPopupService: PoPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.poPaymentPopupService
                    .open(PoPaymentDialogComponent as Component, params['id']);
            } else {
                this.poPaymentPopupService
                    .open(PoPaymentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
