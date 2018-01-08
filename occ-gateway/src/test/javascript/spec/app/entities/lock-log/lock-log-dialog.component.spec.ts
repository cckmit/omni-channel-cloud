/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { LockLogDialogComponent } from '../../../../../../main/webapp/app/entities/lock-log/lock-log-dialog.component';
import { LockLogService } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.service';
import { LockLog } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.model';
import { InventoryService } from '../../../../../../main/webapp/app/entities/inventory';

describe('Component Tests', () => {

    describe('LockLog Management Dialog Component', () => {
        let comp: LockLogDialogComponent;
        let fixture: ComponentFixture<LockLogDialogComponent>;
        let service: LockLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [LockLogDialogComponent],
                providers: [
                    InventoryService,
                    LockLogService
                ]
            })
            .overrideTemplate(LockLogDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LockLogDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LockLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LockLog("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.lockLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lockLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LockLog();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.lockLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lockLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
