import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SaleOrder } from './sale-order.model';
import { SaleOrderPopupService } from './sale-order-popup.service';
import { SaleOrderService } from './sale-order.service';

@Component({
    selector: 'jhi-sale-order-delete-dialog',
    templateUrl: './sale-order-delete-dialog.component.html'
})
export class SaleOrderDeleteDialogComponent {

    saleOrder: SaleOrder;

    constructor(
        private saleOrderService: SaleOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.saleOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'saleOrderListModification',
                content: 'Deleted an saleOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-order-delete-popup',
    template: ''
})
export class SaleOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saleOrderPopupService: SaleOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.saleOrderPopupService
                .open(SaleOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
