/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { CustomerAccountDetailComponent } from '../../../../../../main/webapp/app/entities/customer-account/customer-account-detail.component';
import { CustomerAccountService } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.service';
import { CustomerAccount } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.model';

describe('Component Tests', () => {

    describe('CustomerAccount Management Detail Component', () => {
        let comp: CustomerAccountDetailComponent;
        let fixture: ComponentFixture<CustomerAccountDetailComponent>;
        let service: CustomerAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [CustomerAccountDetailComponent],
                providers: [
                    CustomerAccountService
                ]
            })
            .overrideTemplate(CustomerAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CustomerAccount(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.customerAccount).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
