import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OrderCtrlRule } from './order-ctrl-rule.model';
import { OrderCtrlRuleService } from './order-ctrl-rule.service';

@Component({
    selector: 'jhi-order-ctrl-rule-detail',
    templateUrl: './order-ctrl-rule-detail.component.html'
})
export class OrderCtrlRuleDetailComponent implements OnInit, OnDestroy {

    orderCtrlRule: OrderCtrlRule;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderCtrlRuleService: OrderCtrlRuleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderCtrlRules();
    }

    load(id) {
        this.orderCtrlRuleService.find(id).subscribe((orderCtrlRule) => {
            this.orderCtrlRule = orderCtrlRule;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderCtrlRules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderCtrlRuleListModification',
            (response) => this.load(this.orderCtrlRule.id)
        );
    }
}
