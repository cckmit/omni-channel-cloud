import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OperationType } from './operation-type.model';
import { OperationTypeService } from './operation-type.service';

@Injectable()
export class OperationTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private operationTypeService: OperationTypeService

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
                this.operationTypeService.find(id).subscribe((operationType) => {
                    operationType.ts = this.datePipe
                        .transform(operationType.ts, 'yyyy-MM-ddTHH:mm:ss');
                    operationType.timeCreated = this.datePipe
                        .transform(operationType.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    operationType.timeModified = this.datePipe
                        .transform(operationType.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.operationTypeModalRef(component, operationType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.operationTypeModalRef(component, new OperationType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    operationTypeModalRef(component: Component, operationType: OperationType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operationType = operationType;
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
