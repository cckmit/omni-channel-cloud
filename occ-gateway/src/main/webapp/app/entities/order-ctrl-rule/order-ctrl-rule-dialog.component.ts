import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrderCtrlRule } from './order-ctrl-rule.model';
import { OrderCtrlRulePopupService } from './order-ctrl-rule-popup.service';
import { OrderCtrlRuleService } from './order-ctrl-rule.service';
import { PoType, PoTypeService } from '../po-type';
import { SoType, SoTypeService } from '../so-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-order-ctrl-rule-dialog',
    templateUrl: './order-ctrl-rule-dialog.component.html'
})
export class OrderCtrlRuleDialogComponent implements OnInit {

    orderCtrlRule: OrderCtrlRule;
    isSaving: boolean;

    potypes: PoType[];

    sotypes: SoType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private orderCtrlRuleService: OrderCtrlRuleService,
        private poTypeService: PoTypeService,
        private soTypeService: SoTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.poTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.potypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.soTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.sotypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orderCtrlRule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderCtrlRuleService.update(this.orderCtrlRule));
        } else {
            this.subscribeToSaveResponse(
                this.orderCtrlRuleService.create(this.orderCtrlRule));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrderCtrlRule>) {
        result.subscribe((res: OrderCtrlRule) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OrderCtrlRule) {
        this.eventManager.broadcast({ name: 'orderCtrlRuleListModification', content: 'OK'});
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

    trackSoTypeById(index: number, item: SoType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-ctrl-rule-popup',
    template: ''
})
export class OrderCtrlRulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderCtrlRulePopupService: OrderCtrlRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderCtrlRulePopupService
                    .open(OrderCtrlRuleDialogComponent as Component, params['id']);
            } else {
                this.orderCtrlRulePopupService
                    .open(OrderCtrlRuleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
