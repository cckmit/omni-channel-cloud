import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoState } from './po-state.model';
import { PoStatePopupService } from './po-state-popup.service';
import { PoStateService } from './po-state.service';

@Component({
    selector: 'jhi-po-state-dialog',
    templateUrl: './po-state-dialog.component.html'
})
export class PoStateDialogComponent implements OnInit {

    poState: PoState;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private poStateService: PoStateService,
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
        if (this.poState.id !== undefined) {
            this.subscribeToSaveResponse(
                this.poStateService.update(this.poState));
        } else {
            this.subscribeToSaveResponse(
                this.poStateService.create(this.poState));
        }
    }

    private subscribeToSaveResponse(result: Observable<PoState>) {
        result.subscribe((res: PoState) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PoState) {
        this.eventManager.broadcast({ name: 'poStateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-po-state-popup',
    template: ''
})
export class PoStatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poStatePopupService: PoStatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.poStatePopupService
                    .open(PoStateDialogComponent as Component, params['id']);
            } else {
                this.poStatePopupService
                    .open(PoStateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
