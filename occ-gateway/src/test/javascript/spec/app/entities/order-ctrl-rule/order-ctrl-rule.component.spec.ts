/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { OrderCtrlRuleComponent } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.component';
import { OrderCtrlRuleService } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.service';
import { OrderCtrlRule } from '../../../../../../main/webapp/app/entities/order-ctrl-rule/order-ctrl-rule.model';

describe('Component Tests', () => {

    describe('OrderCtrlRule Management Component', () => {
        let comp: OrderCtrlRuleComponent;
        let fixture: ComponentFixture<OrderCtrlRuleComponent>;
        let service: OrderCtrlRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OrderCtrlRuleComponent],
                providers: [
                    OrderCtrlRuleService
                ]
            })
            .overrideTemplate(OrderCtrlRuleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderCtrlRuleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderCtrlRuleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new OrderCtrlRule(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.orderCtrlRules[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
