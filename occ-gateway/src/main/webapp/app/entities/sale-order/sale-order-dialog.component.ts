import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SaleOrder } from './sale-order.model';
import { SaleOrderPopupService } from './sale-order-popup.service';
import { SaleOrderService } from './sale-order.service';
import { SoType, SoTypeService } from '../so-type';
import { SoState, SoStateService } from '../so-state';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sale-order-dialog',
    templateUrl: './sale-order-dialog.component.html'
})
export class SaleOrderDialogComponent implements OnInit {

    saleOrder: SaleOrder;
    isSaving: boolean;

    sotypes: SoType[];

    sostates: SoState[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private saleOrderService: SaleOrderService,
        private soTypeService: SoTypeService,
        private soStateService: SoStateService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.soTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.sotypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.soStateService.query()
            .subscribe((res: ResponseWrapper) => { this.sostates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.saleOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.saleOrderService.update(this.saleOrder));
        } else {
            this.subscribeToSaveResponse(
                this.saleOrderService.create(this.saleOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<SaleOrder>) {
        result.subscribe((res: SaleOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SaleOrder) {
        this.eventManager.broadcast({ name: 'saleOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSoTypeById(index: number, item: SoType) {
        return item.id;
    }

    trackSoStateById(index: number, item: SoState) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sale-order-popup',
    template: ''
})
export class SaleOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saleOrderPopupService: SaleOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.saleOrderPopupService
                    .open(SaleOrderDialogComponent as Component, params['id']);
            } else {
                this.saleOrderPopupService
                    .open(SaleOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
