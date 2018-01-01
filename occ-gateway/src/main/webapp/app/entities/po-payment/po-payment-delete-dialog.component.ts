import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoPayment } from './po-payment.model';
import { PoPaymentPopupService } from './po-payment-popup.service';
import { PoPaymentService } from './po-payment.service';

@Component({
    selector: 'jhi-po-payment-delete-dialog',
    templateUrl: './po-payment-delete-dialog.component.html'
})
export class PoPaymentDeleteDialogComponent {

    poPayment: PoPayment;

    constructor(
        private poPaymentService: PoPaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.poPaymentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'poPaymentListModification',
                content: 'Deleted an poPayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-payment-delete-popup',
    template: ''
})
export class PoPaymentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poPaymentPopupService: PoPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.poPaymentPopupService
                .open(PoPaymentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
