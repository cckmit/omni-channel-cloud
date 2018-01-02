import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SaleOrder } from './sale-order.model';
import { SaleOrderService } from './sale-order.service';

@Injectable()
export class SaleOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private saleOrderService: SaleOrderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: string | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.saleOrderService.find(id).subscribe((saleOrder) => {
                    saleOrder.orderDate = this.datePipe
                        .transform(saleOrder.orderDate, 'yyyy-MM-ddTHH:mm:ss');
                    saleOrder.ts = this.datePipe
                        .transform(saleOrder.ts, 'yyyy-MM-ddTHH:mm:ss');
                    saleOrder.timeCreated = this.datePipe
                        .transform(saleOrder.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    saleOrder.timeModified = this.datePipe
                        .transform(saleOrder.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.saleOrderModalRef(component, saleOrder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.saleOrderModalRef(component, new SaleOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    saleOrderModalRef(component: Component, saleOrder: SaleOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.saleOrder = saleOrder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
