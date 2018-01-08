/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { OrderCtrlRuleDialogComponent } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule-dialog.component';
import { OrderCtrlRuleService } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.service';
import { OrderCtrlRule } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.model';
import { PoTypeService } from '../../../../../../main/webapp/app/entities/po-type';
import { SoTypeService } from '../../../../../../main/webapp/app/entities/so-type';

describe('Component Tests', () => {

    describe('OrderCtrlRule Management Dialog Component', () => {
        let comp: OrderCtrlRuleDialogComponent;
        let fixture: ComponentFixture<OrderCtrlRuleDialogComponent>;
        let service: OrderCtrlRuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OrderCtrlRuleDialogComponent],
                providers: [
                    PoTypeService,
                    SoTypeService,
                    OrderCtrlRuleService
                ]
            })
            .overrideTemplate(OrderCtrlRuleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderCtrlRuleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderCtrlRuleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OrderCtrlRule("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.orderCtrlRule = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'orderCtrlRuleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OrderCtrlRule();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.orderCtrlRule = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'orderCtrlRuleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
