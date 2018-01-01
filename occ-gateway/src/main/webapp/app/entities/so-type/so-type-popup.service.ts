import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SoType } from './so-type.model';
import { SoTypeService } from './so-type.service';

@Injectable()
export class SoTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private soTypeService: SoTypeService

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
                this.soTypeService.find(id).subscribe((soType) => {
                    soType.ts = this.datePipe
                        .transform(soType.ts, 'yyyy-MM-ddTHH:mm:ss');
                    soType.timeCreated = this.datePipe
                        .transform(soType.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    soType.timeModified = this.datePipe
                        .transform(soType.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.soTypeModalRef(component, soType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.soTypeModalRef(component, new SoType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    soTypeModalRef(component: Component, soType: SoType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.soType = soType;
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
