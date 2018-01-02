import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PoItem } from './po-item.model';
import { PoItemService } from './po-item.service';

@Injectable()
export class PoItemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private poItemService: PoItemService

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
                this.poItemService.find(id).subscribe((poItem) => {
                    poItem.ts = this.datePipe
                        .transform(poItem.ts, 'yyyy-MM-ddTHH:mm:ss');
                    poItem.timeCreated = this.datePipe
                        .transform(poItem.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    poItem.timeModified = this.datePipe
                        .transform(poItem.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.poItemModalRef(component, poItem);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.poItemModalRef(component, new PoItem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    poItemModalRef(component: Component, poItem: PoItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.poItem = poItem;
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
