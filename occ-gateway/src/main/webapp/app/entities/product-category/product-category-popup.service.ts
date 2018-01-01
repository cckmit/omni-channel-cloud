import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ProductCategory } from './product-category.model';
import { ProductCategoryService } from './product-category.service';

@Injectable()
export class ProductCategoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private productCategoryService: ProductCategoryService

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
                this.productCategoryService.find(id).subscribe((productCategory) => {
                    productCategory.ts = this.datePipe
                        .transform(productCategory.ts, 'yyyy-MM-ddTHH:mm:ss');
                    productCategory.timeCreated = this.datePipe
                        .transform(productCategory.timeCreated, 'yyyy-MM-ddTHH:mm:ss');
                    productCategory.timeModified = this.datePipe
                        .transform(productCategory.timeModified, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.productCategoryModalRef(component, productCategory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.productCategoryModalRef(component, new ProductCategory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    productCategoryModalRef(component: Component, productCategory: ProductCategory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productCategory = productCategory;
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