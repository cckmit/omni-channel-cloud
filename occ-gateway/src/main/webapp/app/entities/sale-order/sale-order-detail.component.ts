import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SaleOrder } from './sale-order.model';
import { SaleOrderService } from './sale-order.service';

@Component({
    selector: 'jhi-sale-order-detail',
    templateUrl: './sale-order-detail.component.html'
})
export class SaleOrderDetailComponent implements OnInit, OnDestroy {

    saleOrder: SaleOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private saleOrderService: SaleOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSaleOrders();
    }

    load(id) {
        this.saleOrderService.find(id).subscribe((saleOrder) => {
            this.saleOrder = saleOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSaleOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'saleOrderListModification',
            (response) => this.load(this.saleOrder.id)
        );
    }
}
