import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SoState } from './so-state.model';
import { SoStateService } from './so-state.service';

@Injectable()
export class SoStatePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private soStateService: SoStateService

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
                this.soStateService.find(id).subscribe((soState) => {
                    soState.ts = this.datePipe
                        .transform(soState.ts, 'yyyy-MM-ddTHH:mm:ss');
                    soState.timeCreated = this.datePipe
                        .transform(soState.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    soState.timeModified = this.datePipe
                        .transform(soState.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.soStateModalRef(component, soState);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.soStateModalRef(component, new SoState());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    soStateModalRef(component: Component, soState: SoState): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.soState = soState;
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
