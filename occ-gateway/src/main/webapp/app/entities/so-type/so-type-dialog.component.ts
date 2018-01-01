import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SoType } from './so-type.model';
import { SoTypePopupService } from './so-type-popup.service';
import { SoTypeService } from './so-type.service';

@Component({
    selector: 'jhi-so-type-dialog',
    templateUrl: './so-type-dialog.component.html'
})
export class SoTypeDialogComponent implements OnInit {

    soType: SoType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private soTypeService: SoTypeService,
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
        if (this.soType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.soTypeService.update(this.soType));
        } else {
            this.subscribeToSaveResponse(
                this.soTypeService.create(this.soType));
        }
    }

    private subscribeToSaveResponse(result: Observable<SoType>) {
        result.subscribe((res: SoType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SoType) {
        this.eventManager.broadcast({ name: 'soTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-so-type-popup',
    template: ''
})
export class SoTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private soTypePopupService: SoTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.soTypePopupService
                    .open(SoTypeDialogComponent as Component, params['id']);
            } else {
                this.soTypePopupService
                    .open(SoTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
