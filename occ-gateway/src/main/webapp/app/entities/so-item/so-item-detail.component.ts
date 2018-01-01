import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SoItem } from './so-item.model';
import { SoItemService } from './so-item.service';

@Component({
    selector: 'jhi-so-item-detail',
    templateUrl: './so-item-detail.component.html'
})
export class SoItemDetailComponent implements OnInit, OnDestroy {

    soItem: SoItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private soItemService: SoItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSoItems();
    }

    load(id) {
        this.soItemService.find(id).subscribe((soItem) => {
            this.soItem = soItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSoItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'soItemListModification',
            (response) => this.load(this.soItem.id)
        );
    }
}
