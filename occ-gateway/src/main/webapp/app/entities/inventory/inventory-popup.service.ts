import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Inventory } from './inventory.model';
import { InventoryService } from './inventory.service';

@Injectable()
export class InventoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private inventoryService: InventoryService

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
                this.inventoryService.find(id).subscribe((inventory) => {
                    inventory.ts = this.datePipe
                        .transform(inventory.ts, 'yyyy-MM-ddTHH:mm:ss');
                    inventory.timeCreated = this.datePipe
                        .transform(inventory.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    inventory.timeModified = this.datePipe
                        .transform(inventory.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.inventoryModalRef(component, inventory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inventoryModalRef(component, new Inventory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inventoryModalRef(component: Component, inventory: Inventory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inventory = inventory;
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
