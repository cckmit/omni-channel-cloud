import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PoType } from './po-type.model';
import { PoTypeService } from './po-type.service';

@Injectable()
export class PoTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private poTypeService: PoTypeService

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
                this.poTypeService.find(id).subscribe((poType) => {
                    poType.ts = this.datePipe
                        .transform(poType.ts, 'yyyy-MM-ddTHH:mm:ss');
                    poType.timeCreated = this.datePipe
                        .transform(poType.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    poType.timeModified = this.datePipe
                        .transform(poType.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.poTypeModalRef(component, poType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.poTypeModalRef(component, new PoType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    poTypeModalRef(component: Component, poType: PoType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.poType = poType;
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
