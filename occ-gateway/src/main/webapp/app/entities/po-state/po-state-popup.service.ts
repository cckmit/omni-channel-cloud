import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PoState } from './po-state.model';
import { PoStateService } from './po-state.service';

@Injectable()
export class PoStatePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private poStateService: PoStateService

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
                this.poStateService.find(id).subscribe((poState) => {
                    poState.ts = this.datePipe
                        .transform(poState.ts, 'yyyy-MM-ddTHH:mm:ss');
                    poState.timeCreated = this.datePipe
                        .transform(poState.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    poState.timeModified = this.datePipe
                        .transform(poState.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.poStateModalRef(component, poState);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.poStateModalRef(component, new PoState());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    poStateModalRef(component: Component, poState: PoState): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.poState = poState;
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
