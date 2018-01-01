/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { PurchaseOrderComponent } from '../../../../../../main/webapp/app/entities/purchase-order/purchase-order.component';
import { PurchaseOrderService } from '../../../../../../main/webapp/app/entities/purchase-order/purchase-order.service';
import { PurchaseOrder } from '../../../../../../main/webapp/app/entities/purchase-order/purchase-order.model';

describe('Component Tests', () => {

    describe('PurchaseOrder Management Component', () => {
        let comp: PurchaseOrderComponent;
        let fixture: ComponentFixture<PurchaseOrderComponent>;
        let service: PurchaseOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PurchaseOrderComponent],
                providers: [
                    PurchaseOrderService
                ]
            })
            .overrideTemplate(PurchaseOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PurchaseOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PurchaseOrder(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.purchaseOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
