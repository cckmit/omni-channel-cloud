import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OperationLog } from './operation-log.model';
import { OperationLogService } from './operation-log.service';

@Injectable()
export class OperationLogPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private operationLogService: OperationLogService

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
                this.operationLogService.find(id).subscribe((operationLog) => {
                    operationLog.ts = this.datePipe
                        .transform(operationLog.ts, 'yyyy-MM-ddTHH:mm:ss');
                    operationLog.timeCreated = this.datePipe
                        .transform(operationLog.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    operationLog.timeModified = this.datePipe
                        .transform(operationLog.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.operationLogModalRef(component, operationLog);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.operationLogModalRef(component, new OperationLog());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    operationLogModalRef(component: Component, operationLog: OperationLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operationLog = operationLog;
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
