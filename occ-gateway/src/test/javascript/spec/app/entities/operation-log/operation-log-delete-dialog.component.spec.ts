/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { OperationLogDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/operation-log/operation-log-delete-dialog.component';
import { OperationLogService } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.service';

describe('Component Tests', () => {

    describe('OperationLog Management Delete Component', () => {
        let comp: OperationLogDeleteDialogComponent;
        let fixture: ComponentFixture<OperationLogDeleteDialogComponent>;
        let service: OperationLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OperationLogDeleteDialogComponent],
                providers: [
                    OperationLogService
                ]
            })
            .overrideTemplate(OperationLogDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
