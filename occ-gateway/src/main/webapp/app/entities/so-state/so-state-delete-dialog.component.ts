import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SoState } from './so-state.model';
import { SoStatePopupService } from './so-state-popup.service';
import { SoStateService } from './so-state.service';

@Component({
    selector: 'jhi-so-state-delete-dialog',
    templateUrl: './so-state-delete-dialog.component.html'
})
export class SoStateDeleteDialogComponent {

    soState: SoState;

    constructor(
        private soStateService: SoStateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.soStateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'soStateListModification',
                content: 'Deleted an soState'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-so-state-delete-popup',
    template: ''
})
export class SoStateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private soStatePopupService: SoStatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.soStatePopupService
                .open(SoStateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
