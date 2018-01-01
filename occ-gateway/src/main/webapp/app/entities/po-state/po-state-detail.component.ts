import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PoState } from './po-state.model';
import { PoStateService } from './po-state.service';

@Component({
    selector: 'jhi-po-state-detail',
    templateUrl: './po-state-detail.component.html'
})
export class PoStateDetailComponent implements OnInit, OnDestroy {

    poState: PoState;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private poStateService: PoStateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPoStates();
    }

    load(id) {
        this.poStateService.find(id).subscribe((poState) => {
            this.poState = poState;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPoStates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'poStateListModification',
            (response) => this.load(this.poState.id)
        );
    }
}
