/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { PoItemDialogComponent } from '../../../../../../main/webapp/app/entities/po-item/po-item-dialog.component';
import { PoItemService } from '../../../../../../main/webapp/app/entities/po-item/po-item.service';
import { PoItem } from '../../../../../../main/webapp/app/entities/po-item/po-item.model';
import { PoStateService } from '../../../../../../main/webapp/app/entities/po-state';
import { PurchaseOrderService } from '../../../../../../main/webapp/app/entities/purchase-order';

describe('Component Tests', () => {

    describe('PoItem Management Dialog Component', () => {
        let comp: PoItemDialogComponent;
        let fixture: ComponentFixture<PoItemDialogComponent>;
        let service: PoItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoItemDialogComponent],
                providers: [
                    PoStateService,
                    PurchaseOrderService,
                    PoItemService
                ]
            })
            .overrideTemplate(PoItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.poItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.poItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
