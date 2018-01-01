import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CustomerAccount } from './customer-account.model';
import { CustomerAccountPopupService } from './customer-account-popup.service';
import { CustomerAccountService } from './customer-account.service';
import { Customer, CustomerService } from '../customer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-customer-account-dialog',
    templateUrl: './customer-account-dialog.component.html'
})
export class CustomerAccountDialogComponent implements OnInit {

    customerAccount: CustomerAccount;
    isSaving: boolean;

    customers: Customer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private customerAccountService: CustomerAccountService,
        private customerService: CustomerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.customerService.query()
            .subscribe((res: ResponseWrapper) => { this.customers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customerAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerAccountService.update(this.customerAccount));
        } else {
            this.subscribeToSaveResponse(
                this.customerAccountService.create(this.customerAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<CustomerAccount>) {
        result.subscribe((res: CustomerAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CustomerAccount) {
        this.eventManager.broadcast({ name: 'customerAccountListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-account-popup',
    template: ''
})
export class CustomerAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAccountPopupService: CustomerAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.customerAccountPopupService
                    .open(CustomerAccountDialogComponent as Component, params['id']);
            } else {
                this.customerAccountPopupService
                    .open(CustomerAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
