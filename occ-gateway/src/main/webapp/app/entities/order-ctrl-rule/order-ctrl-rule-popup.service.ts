import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OrderCtrlRule } from './order-ctrl-rule.model';
import { OrderCtrlRuleService } from './order-ctrl-rule.service';

@Injectable()
export class OrderCtrlRulePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private orderCtrlRuleService: OrderCtrlRuleService

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
                this.orderCtrlRuleService.find(id).subscribe((orderCtrlRule) => {
                    orderCtrlRule.ts = this.datePipe
                        .transform(orderCtrlRule.ts, 'yyyy-MM-ddTHH:mm:ss');
                    orderCtrlRule.timeCreated = this.datePipe
                        .transform(orderCtrlRule.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    orderCtrlRule.timeModified = this.datePipe
                        .transform(orderCtrlRule.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.orderCtrlRuleModalRef(component, orderCtrlRule);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.orderCtrlRuleModalRef(component, new OrderCtrlRule());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    orderCtrlRuleModalRef(component: Component, orderCtrlRule: OrderCtrlRule): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderCtrlRule = orderCtrlRule;
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
