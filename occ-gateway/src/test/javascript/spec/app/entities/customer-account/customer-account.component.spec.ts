/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { CustomerAccountComponent } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.component';
import { CustomerAccountService } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.service';
import { CustomerAccount } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.model';

describe('Component Tests', () => {

    describe('CustomerAccount Management Component', () => {
        let comp: CustomerAccountComponent;
        let fixture: ComponentFixture<CustomerAccountComponent>;
        let service: CustomerAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [CustomerAccountComponent],
                providers: [
                    CustomerAccountService
                ]
            })
            .overrideTemplate(CustomerAccountComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAccountComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CustomerAccount("123")],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.customerAccounts[0]).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
