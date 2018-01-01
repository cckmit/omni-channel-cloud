import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrderCtrlRule } from './order-ctrl-rule.model';
import { OrderCtrlRulePopupService } from './order-ctrl-rule-popup.service';
import { OrderCtrlRuleService } from './order-ctrl-rule.service';

@Component({
    selector: 'jhi-order-ctrl-rule-delete-dialog',
    templateUrl: './order-ctrl-rule-delete-dialog.component.html'
})
export class OrderCtrlRuleDeleteDialogComponent {

    orderCtrlRule: OrderCtrlRule;

    constructor(
        private orderCtrlRuleService: OrderCtrlRuleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderCtrlRuleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderCtrlRuleListModification',
                content: 'Deleted an orderCtrlRule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-ctrl-rule-delete-popup',
    template: ''
})
export class OrderCtrlRuleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderCtrlRulePopupService: OrderCtrlRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderCtrlRulePopupService
                .open(OrderCtrlRuleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
