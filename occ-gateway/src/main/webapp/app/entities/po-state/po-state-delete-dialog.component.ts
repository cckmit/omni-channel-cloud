import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoState } from './po-state.model';
import { PoStatePopupService } from './po-state-popup.service';
import { PoStateService } from './po-state.service';

@Component({
    selector: 'jhi-po-state-delete-dialog',
    templateUrl: './po-state-delete-dialog.component.html'
})
export class PoStateDeleteDialogComponent {

    poState: PoState;

    constructor(
        private poStateService: PoStateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.poStateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'poStateListModification',
                content: 'Deleted an poState'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-state-delete-popup',
    template: ''
})
export class PoStateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poStatePopupService: PoStatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.poStatePopupService
                .open(PoStateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
