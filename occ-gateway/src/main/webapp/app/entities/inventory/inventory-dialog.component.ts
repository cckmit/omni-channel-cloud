import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Inventory } from './inventory.model';
import { InventoryPopupService } from './inventory-popup.service';
import { InventoryService } from './inventory.service';

@Component({
    selector: 'jhi-inventory-dialog',
    templateUrl: './inventory-dialog.component.html'
})
export class InventoryDialogComponent implements OnInit {

    inventory: Inventory;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private inventoryService: InventoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inventory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventoryService.update(this.inventory));
        } else {
            this.subscribeToSaveResponse(
                this.inventoryService.create(this.inventory));
        }
    }

    private subscribeToSaveResponse(result: Observable<Inventory>) {
        result.subscribe((res: Inventory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Inventory) {
        this.eventManager.broadcast({ name: 'inventoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-inventory-popup',
    template: ''
})
export class InventoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventoryPopupService: InventoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inventoryPopupService
                    .open(InventoryDialogComponent as Component, params['id']);
            } else {
                this.inventoryPopupService
                    .open(InventoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
