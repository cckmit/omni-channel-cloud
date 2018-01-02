import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoItem } from './po-item.model';
import { PoItemPopupService } from './po-item-popup.service';
import { PoItemService } from './po-item.service';

@Component({
    selector: 'jhi-po-item-delete-dialog',
    templateUrl: './po-item-delete-dialog.component.html'
})
export class PoItemDeleteDialogComponent {

    poItem: PoItem;

    constructor(
        private poItemService: PoItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.poItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'poItemListModification',
                content: 'Deleted an poItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-item-delete-popup',
    template: ''
})
export class PoItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poItemPopupService: PoItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.poItemPopupService
                .open(PoItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
