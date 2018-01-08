/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { PoPaymentComponent } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.component';
import { PoPaymentService } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.service';
import { PoPayment } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.model';

describe('Component Tests', () => {

    describe('PoPayment Management Component', () => {
        let comp: PoPaymentComponent;
        let fixture: ComponentFixture<PoPaymentComponent>;
        let service: PoPaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoPaymentComponent],
                providers: [
                    PoPaymentService
                ]
            })
            .overrideTemplate(PoPaymentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoPaymentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoPaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PoPayment("123")],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.poPayments[0]).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
