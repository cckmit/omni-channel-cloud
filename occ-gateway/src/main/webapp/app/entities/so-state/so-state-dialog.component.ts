import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SoState } from './so-state.model';
import { SoStatePopupService } from './so-state-popup.service';
import { SoStateService } from './so-state.service';

@Component({
    selector: 'jhi-so-state-dialog',
    templateUrl: './so-state-dialog.component.html'
})
export class SoStateDialogComponent implements OnInit {

    soState: SoState;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private soStateService: SoStateService,
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
        if (this.soState.id !== undefined) {
            this.subscribeToSaveResponse(
                this.soStateService.update(this.soState));
        } else {
            this.subscribeToSaveResponse(
                this.soStateService.create(this.soState));
        }
    }

    private subscribeToSaveResponse(result: Observable<SoState>) {
        result.subscribe((res: SoState) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SoState) {
        this.eventManager.broadcast({ name: 'soStateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-so-state-popup',
    template: ''
})
export class SoStatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private soStatePopupService: SoStatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.soStatePopupService
                    .open(SoStateDialogComponent as Component, params['id']);
            } else {
                this.soStatePopupService
                    .open(SoStateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
