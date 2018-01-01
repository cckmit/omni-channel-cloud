import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SoType } from './so-type.model';
import { SoTypePopupService } from './so-type-popup.service';
import { SoTypeService } from './so-type.service';

@Component({
    selector: 'jhi-so-type-delete-dialog',
    templateUrl: './so-type-delete-dialog.component.html'
})
export class SoTypeDeleteDialogComponent {

    soType: SoType;

    constructor(
        private soTypeService: SoTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.soTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'soTypeListModification',
                content: 'Deleted an soType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-so-type-delete-popup',
    template: ''
})
export class SoTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private soTypePopupService: SoTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.soTypePopupService
                .open(SoTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
