import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SoState } from './so-state.model';
import { SoStateService } from './so-state.service';

@Component({
    selector: 'jhi-so-state-detail',
    templateUrl: './so-state-detail.component.html'
})
export class SoStateDetailComponent implements OnInit, OnDestroy {

    soState: SoState;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private soStateService: SoStateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSoStates();
    }

    load(id) {
        this.soStateService.find(id).subscribe((soState) => {
            this.soState = soState;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSoStates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'soStateListModification',
            (response) => this.load(this.soState.id)
        );
    }
}
