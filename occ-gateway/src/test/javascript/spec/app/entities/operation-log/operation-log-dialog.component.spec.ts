/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { OperationLogDialogComponent } from '../../../../../../main/webapp/app/entities/operation-log/operation-log-dialog.component';
import { OperationLogService } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.service';
import { OperationLog } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.model';
import { OperationTypeService } from '../../../../../../main/webapp/app/entities/operation-type';
import { InventoryService } from '../../../../../../main/webapp/app/entities/inventory';

describe('Component Tests', () => {

    describe('OperationLog Management Dialog Component', () => {
        let comp: OperationLogDialogComponent;
        let fixture: ComponentFixture<OperationLogDialogComponent>;
        let service: OperationLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OperationLogDialogComponent],
                providers: [
                    OperationTypeService,
                    InventoryService,
                    OperationLogService
                ]
            })
            .overrideTemplate(OperationLogDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationLogDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OperationLog("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.operationLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'operationLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OperationLog();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.operationLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'operationLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
