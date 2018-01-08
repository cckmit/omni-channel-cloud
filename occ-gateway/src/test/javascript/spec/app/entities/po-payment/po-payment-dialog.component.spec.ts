/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { PoPaymentDialogComponent } from '../../../../../../main/webapp/app/entities/po-payment/po-payment-dialog.component';
import { PoPaymentService } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.service';
import { PoPayment } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.model';
import { PurchaseOrderService } from '../../../../../../main/webapp/app/entities/purchase-order';

describe('Component Tests', () => {

    describe('PoPayment Management Dialog Component', () => {
        let comp: PoPaymentDialogComponent;
        let fixture: ComponentFixture<PoPaymentDialogComponent>;
        let service: PoPaymentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoPaymentDialogComponent],
                providers: [
                    PurchaseOrderService,
                    PoPaymentService
                ]
            })
            .overrideTemplate(PoPaymentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoPaymentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoPaymentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoPayment("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.poPayment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poPaymentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoPayment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.poPayment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poPaymentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
