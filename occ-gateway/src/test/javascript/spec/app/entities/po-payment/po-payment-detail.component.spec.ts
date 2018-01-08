/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { PoPaymentDetailComponent } from '../../../../../../main/webapp/app/entities/po-payment/po-payment-detail.component';
import { PoPaymentService } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.service';
import { PoPayment } from '../../../../../../main/webapp/app/entities/po-payment/po-payment.model';

describe('Component Tests', () => {

    describe('PoPayment Management Detail Component', () => {
        let comp: PoPaymentDetailComponent;
        let fixture: ComponentFixture<PoPaymentDetailComponent>;
        let service: PoPaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoPaymentDetailComponent],
                providers: [
                    PoPaymentService
                ]
            })
            .overrideTemplate(PoPaymentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoPaymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoPaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PoPayment("123")));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith("123");
                expect(comp.poPayment).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
