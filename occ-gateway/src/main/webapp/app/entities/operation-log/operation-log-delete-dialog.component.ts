import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OperationLog } from './operation-log.model';
import { OperationLogPopupService } from './operation-log-popup.service';
import { OperationLogService } from './operation-log.service';

@Component({
    selector: 'jhi-operation-log-delete-dialog',
    templateUrl: './operation-log-delete-dialog.component.html'
})
export class OperationLogDeleteDialogComponent {

    operationLog: OperationLog;

    constructor(
        private operationLogService: OperationLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operationLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'operationLogListModification',
                content: 'Deleted an operationLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operation-log-delete-popup',
    template: ''
})
export class OperationLogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationLogPopupService: OperationLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.operationLogPopupService
                .open(OperationLogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
