import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoType } from './po-type.model';
import { PoTypePopupService } from './po-type-popup.service';
import { PoTypeService } from './po-type.service';

@Component({
    selector: 'jhi-po-type-delete-dialog',
    templateUrl: './po-type-delete-dialog.component.html'
})
export class PoTypeDeleteDialogComponent {

    poType: PoType;

    constructor(
        private poTypeService: PoTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.poTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'poTypeListModification',
                content: 'Deleted an poType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-type-delete-popup',
    template: ''
})
export class PoTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poTypePopupService: PoTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.poTypePopupService
                .open(PoTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
