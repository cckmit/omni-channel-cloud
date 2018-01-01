/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { OrderCtrlRuleDetailComponent } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule-detail.component';
import { OrderCtrlRuleService } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.service';
import { OrderCtrlRule } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.model';

describe('Component Tests', () => {

    describe('OrderCtrlRule Management Detail Component', () => {
        let comp: OrderCtrlRuleDetailComponent;
        let fixture: ComponentFixture<OrderCtrlRuleDetailComponent>;
        let service: OrderCtrlRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OrderCtrlRuleDetailComponent],
                providers: [
                    OrderCtrlRuleService
                ]
            })
            .overrideTemplate(OrderCtrlRuleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderCtrlRuleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderCtrlRuleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new OrderCtrlRule(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.orderCtrlRule).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
