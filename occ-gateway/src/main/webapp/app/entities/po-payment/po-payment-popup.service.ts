import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PoPayment } from './po-payment.model';
import { PoPaymentService } from './po-payment.service';

@Injectable()
export class PoPaymentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private poPaymentService: PoPaymentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.poPaymentService.find(id).subscribe((poPayment) => {
                    poPayment.timePaid = this.datePipe
                        .transform(poPayment.timePaid, 'yyyy-MM-ddTHH:mm:ss');
                    poPayment.ts = this.datePipe
                        .transform(poPayment.ts, 'yyyy-MM-ddTHH:mm:ss');
                    poPayment.timeCreated = this.datePipe
                        .transform(poPayment.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    poPayment.timeModified = this.datePipe
                        .transform(poPayment.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.poPaymentModalRef(component, poPayment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.poPaymentModalRef(component, new PoPayment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    poPaymentModalRef(component: Component, poPayment: PoPayment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.poPayment = poPayment;
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
