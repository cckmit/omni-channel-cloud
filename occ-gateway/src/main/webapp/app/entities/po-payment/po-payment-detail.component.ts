import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PoPayment } from './po-payment.model';
import { PoPaymentService } from './po-payment.service';

@Component({
    selector: 'jhi-po-payment-detail',
    templateUrl: './po-payment-detail.component.html'
})
export class PoPaymentDetailComponent implements OnInit, OnDestroy {

    poPayment: PoPayment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private poPaymentService: PoPaymentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPoPayments();
    }

    load(id) {
        this.poPaymentService.find(id).subscribe((poPayment) => {
            this.poPayment = poPayment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPoPayments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'poPaymentListModification',
            (response) => this.load(this.poPayment.id)
        );
    }
}
