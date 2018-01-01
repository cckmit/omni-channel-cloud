import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SoItem } from './so-item.model';
import { SoItemPopupService } from './so-item-popup.service';
import { SoItemService } from './so-item.service';
import { SoState, SoStateService } from '../so-state';
import { SaleOrder, SaleOrderService } from '../sale-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-so-item-dialog',
    templateUrl: './so-item-dialog.component.html'
})
export class SoItemDialogComponent implements OnInit {

    soItem: SoItem;
    isSaving: boolean;

    sostates: SoState[];

    saleorders: SaleOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private soItemService: SoItemService,
        private soStateService: SoStateService,
        private saleOrderService: SaleOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.soStateService.query()
            .subscribe((res: ResponseWrapper) => { this.sostates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.saleOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.saleorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.soItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.soItemService.update(this.soItem));
        } else {
            this.subscribeToSaveResponse(
                this.soItemService.create(this.soItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<SoItem>) {
        result.subscribe((res: SoItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SoItem) {
        this.eventManager.broadcast({ name: 'soItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSoStateById(index: number, item: SoState) {
        return item.id;
    }

    trackSaleOrderById(index: number, item: SaleOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-so-item-popup',
    template: ''
})
export class SoItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private soItemPopupService: SoItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.soItemPopupService
                    .open(SoItemDialogComponent as Component, params['id']);
            } else {
                this.soItemPopupService
                    .open(SoItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
