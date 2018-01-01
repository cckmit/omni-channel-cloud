/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { SaleOrderComponent } from '../../../../../../main/webapp/app/entities/sale-order/sale-order.component';
import { SaleOrderService } from '../../../../../../main/webapp/app/entities/sale-order/sale-order.service';
import { SaleOrder } from '../../../../../../main/webapp/app/entities/sale-order/sale-order.model';

describe('Component Tests', () => {

    describe('SaleOrder Management Component', () => {
        let comp: SaleOrderComponent;
        let fixture: ComponentFixture<SaleOrderComponent>;
        let service: SaleOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SaleOrderComponent],
                providers: [
                    SaleOrderService
                ]
            })
            .overrideTemplate(SaleOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SaleOrder(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.saleOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
