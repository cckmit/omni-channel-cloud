import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoType } from './po-type.model';
import { PoTypePopupService } from './po-type-popup.service';
import { PoTypeService } from './po-type.service';

@Component({
    selector: 'jhi-po-type-dialog',
    templateUrl: './po-type-dialog.component.html'
})
export class PoTypeDialogComponent implements OnInit {

    poType: PoType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private poTypeService: PoTypeService,
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
        if (this.poType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.poTypeService.update(this.poType));
        } else {
            this.subscribeToSaveResponse(
                this.poTypeService.create(this.poType));
        }
    }

    private subscribeToSaveResponse(result: Observable<PoType>) {
        result.subscribe((res: PoType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PoType) {
        this.eventManager.broadcast({ name: 'poTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-po-type-popup',
    template: ''
})
export class PoTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private poTypePopupService: PoTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.poTypePopupService
                    .open(PoTypeDialogComponent as Component, params['id']);
            } else {
                this.poTypePopupService
                    .open(PoTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
