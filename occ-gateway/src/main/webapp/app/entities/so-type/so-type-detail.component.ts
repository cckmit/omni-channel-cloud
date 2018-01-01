import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SoType } from './so-type.model';
import { SoTypeService } from './so-type.service';

@Component({
    selector: 'jhi-so-type-detail',
    templateUrl: './so-type-detail.component.html'
})
export class SoTypeDetailComponent implements OnInit, OnDestroy {

    soType: SoType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private soTypeService: SoTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSoTypes();
    }

    load(id) {
        this.soTypeService.find(id).subscribe((soType) => {
            this.soType = soType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSoTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'soTypeListModification',
            (response) => this.load(this.soType.id)
        );
    }
}
