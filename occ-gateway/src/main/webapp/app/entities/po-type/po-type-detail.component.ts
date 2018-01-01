import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PoType } from './po-type.model';
import { PoTypeService } from './po-type.service';

@Component({
    selector: 'jhi-po-type-detail',
    templateUrl: './po-type-detail.component.html'
})
export class PoTypeDetailComponent implements OnInit, OnDestroy {

    poType: PoType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private poTypeService: PoTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPoTypes();
    }

    load(id) {
        this.poTypeService.find(id).subscribe((poType) => {
            this.poType = poType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPoTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'poTypeListModification',
            (response) => this.load(this.poType.id)
        );
    }
}
