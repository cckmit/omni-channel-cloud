import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { LockLog } from './lock-log.model';
import { LockLogService } from './lock-log.service';

@Injectable()
export class LockLogPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private lockLogService: LockLogService

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
                this.lockLogService.find(id).subscribe((lockLog) => {
                    lockLog.ts = this.datePipe
                        .transform(lockLog.ts, 'yyyy-MM-ddTHH:mm:ss');
                    lockLog.timeCreated = this.datePipe
                        .transform(lockLog.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    lockLog.timeModified = this.datePipe
                        .transform(lockLog.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.lockLogModalRef(component, lockLog);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lockLogModalRef(component, new LockLog());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lockLogModalRef(component: Component, lockLog: LockLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lockLog = lockLog;
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
