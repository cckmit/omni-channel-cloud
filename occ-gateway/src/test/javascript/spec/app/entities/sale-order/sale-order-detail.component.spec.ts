/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { SaleOrderDetailComponent } from '../../../../../../main/webapp/app/entities/sale-order/sale-order-detail.component';
import { SaleOrderService } from '../../../../../../main/webapp/app/entities/sale-order/sale-order.service';
import { SaleOrder } from '../../../../../../main/webapp/app/entities/sale-order/sale-order.model';

describe('Component Tests', () => {

    describe('SaleOrder Management Detail Component', () => {
        let comp: SaleOrderDetailComponent;
        let fixture: ComponentFixture<SaleOrderDetailComponent>;
        let service: SaleOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SaleOrderDetailComponent],
                providers: [
                    SaleOrderService
                ]
            })
            .overrideTemplate(SaleOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SaleOrder(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.saleOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
