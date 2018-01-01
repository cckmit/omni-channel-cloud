/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { OrderCtrlRuleDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule-delete-dialog.component';
import { OrderCtrlRuleService } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.service';

describe('Component Tests', () => {

    describe('OrderCtrlRule Management Delete Component', () => {
        let comp: OrderCtrlRuleDeleteDialogComponent;
        let fixture: ComponentFixture<OrderCtrlRuleDeleteDialogComponent>;
        let service: OrderCtrlRuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OrderCtrlRuleDeleteDialogComponent],
                providers: [
                    OrderCtrlRuleService
                ]
            })
            .overrideTemplate(OrderCtrlRuleDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderCtrlRuleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderCtrlRuleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
