import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SoItem } from './so-item.model';
import { SoItemPopupService } from './so-item-popup.service';
import { SoItemService } from './so-item.service';

@Component({
    selector: 'jhi-so-item-delete-dialog',
    templateUrl: './so-item-delete-dialog.component.html'
})
export class SoItemDeleteDialogComponent {

    soItem: SoItem;

    constructor(
        private soItemService: SoItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.soItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'soItemListModification',
                content: 'Deleted an soItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-so-item-delete-popup',
    template: ''
})
export class SoItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private soItemPopupService: SoItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.soItemPopupService
                .open(SoItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
