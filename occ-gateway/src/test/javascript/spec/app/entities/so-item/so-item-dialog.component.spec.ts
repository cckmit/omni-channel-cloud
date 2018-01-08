/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { SoItemDialogComponent } from '../../../../../../main/webapp/app/entities/so-item/so-item-dialog.component';
import { SoItemService } from '../../../../../../main/webapp/app/entities/so-item/so-item.service';
import { SoItem } from '../../../../../../main/webapp/app/entities/so-item/so-item.model';
import { SoStateService } from '../../../../../../main/webapp/app/entities/so-state';
import { SaleOrderService } from '../../../../../../main/webapp/app/entities/sale-order';

describe('Component Tests', () => {

    describe('SoItem Management Dialog Component', () => {
        let comp: SoItemDialogComponent;
        let fixture: ComponentFixture<SoItemDialogComponent>;
        let service: SoItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoItemDialogComponent],
                providers: [
                    SoStateService,
                    SaleOrderService,
                    SoItemService
                ]
            })
            .overrideTemplate(SoItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SoItem("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.soItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'soItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SoItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.soItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'soItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
