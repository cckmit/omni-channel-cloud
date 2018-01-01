import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PoItem } from './po-item.model';
import { PoItemPopupService } from './po-item-popup.service';
import { PoItemService } from './po-item.service';
import { PoState, PoStateService } from '../po-state';
import { PurchaseOrder, PurchaseOrderService } from '../purchase-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-po-item-dialog',
    templateUrl: './po-item-dialog.component.html'
})
export class PoItemDialogComponent implements OnInit {

    poItem: PoItem;
    isSaving: boolean;

    postates: PoState[];

    purchaseorders: PurchaseOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private poItemService: PoItemService,
        private poStateService: PoStateService,
        private purchaseOrderService: PurchaseOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.poStateService.query()
            .subscribe((res: ResponseWrapper) => { this.postates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.purchaseOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.purchaseorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.poItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.poItemService.update(this.poItem));
        } else {
            this.subscribeToSaveResponse(
                this.poItemService.create(this.poItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<PoItem>) {
        result.subscribe((res: PoItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PoItem) {
        this.eventManager.broadcast({ name: 'poItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPoStateById(index: number, item: PoState) {
        return item.id;
    }

    trackPurchaseOrderById(index: number, item: PurchaseOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-po-item-popup',
    template: ''
})
export class PoItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poItemPopupService: PoItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.poItemPopupService
                    .open(PoItemDialogComponent as Component, params['id']);
            } else {
                this.poItemPopupService
                    .open(PoItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
