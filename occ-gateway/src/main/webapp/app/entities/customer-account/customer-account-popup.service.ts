import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CustomerAccount } from './customer-account.model';
import { CustomerAccountService } from './customer-account.service';

@Injectable()
export class CustomerAccountPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private customerAccountService: CustomerAccountService

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
                this.customerAccountService.find(id).subscribe((customerAccount) => {
                    customerAccount.ts = this.datePipe
                        .transform(customerAccount.ts, 'yyyy-MM-ddTHH:mm:ss');
                    customerAccount.timeCreated = this.datePipe
                        .transform(customerAccount.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    customerAccount.timeModified = this.datePipe
                        .transform(customerAccount.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.customerAccountModalRef(component, customerAccount);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.customerAccountModalRef(component, new CustomerAccount());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    customerAccountModalRef(component: Component, customerAccount: CustomerAccount): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerAccount = customerAccount;
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
