import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PoItem } from './po-item.model';
import { PoItemService } from './po-item.service';

@Component({
    selector: 'jhi-po-item-detail',
    templateUrl: './po-item-detail.component.html'
})
export class PoItemDetailComponent implements OnInit, OnDestroy {

    poItem: PoItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private poItemService: PoItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPoItems();
    }

    load(id) {
        this.poItemService.find(id).subscribe((poItem) => {
            this.poItem = poItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPoItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'poItemListModification',
            (response) => this.load(this.poItem.id)
        );
    }
}
