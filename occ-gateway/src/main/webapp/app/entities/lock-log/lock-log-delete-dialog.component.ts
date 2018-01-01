import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LockLog } from './lock-log.model';
import { LockLogPopupService } from './lock-log-popup.service';
import { LockLogService } from './lock-log.service';

@Component({
    selector: 'jhi-lock-log-delete-dialog',
    templateUrl: './lock-log-delete-dialog.component.html'
})
export class LockLogDeleteDialogComponent {

    lockLog: LockLog;

    constructor(
        private lockLogService: LockLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lockLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lockLogListModification',
                content: 'Deleted an lockLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lock-log-delete-popup',
    template: ''
})
export class LockLogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lockLogPopupService: LockLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lockLogPopupService
                .open(LockLogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
