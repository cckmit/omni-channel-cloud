/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { CustomerAccountDialogComponent } from '../../../../../../main/webapp/app/entities/customer-account/customer-account-dialog.component';
import { CustomerAccountService } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.service';
import { CustomerAccount } from '../../../../../../main/webapp/app/entities/customer-account/customer-account.model';
import { CustomerService } from '../../../../../../main/webapp/app/entities/customer';

describe('Component Tests', () => {

    describe('CustomerAccount Management Dialog Component', () => {
        let comp: CustomerAccountDialogComponent;
        let fixture: ComponentFixture<CustomerAccountDialogComponent>;
        let service: CustomerAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [CustomerAccountDialogComponent],
                providers: [
                    CustomerService,
                    CustomerAccountService
                ]
            })
            .overrideTemplate(CustomerAccountDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAccountDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAccountService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CustomerAccount(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.customerAccount = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'customerAccountListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CustomerAccount();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.customerAccount = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'customerAccountListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
