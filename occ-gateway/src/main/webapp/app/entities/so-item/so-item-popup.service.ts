import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SoItem } from './so-item.model';
import { SoItemService } from './so-item.service';

@Injectable()
export class SoItemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private soItemService: SoItemService

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
                this.soItemService.find(id).subscribe((soItem) => {
                    soItem.ts = this.datePipe
                        .transform(soItem.ts, 'yyyy-MM-ddTHH:mm:ss');
                    soItem.timeCreated = this.datePipe
                        .transform(soItem.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    soItem.timeModified = this.datePipe
                        .transform(soItem.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.soItemModalRef(component, soItem);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.soItemModalRef(component, new SoItem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    soItemModalRef(component: Component, soItem: SoItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.soItem = soItem;
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
