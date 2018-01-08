/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { LockLogDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/lock-log/lock-log-delete-dialog.component';
import { LockLogService } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.service';

describe('Component Tests', () => {

    describe('LockLog Management Delete Component', () => {
        let comp: LockLogDeleteDialogComponent;
        let fixture: ComponentFixture<LockLogDeleteDialogComponent>;
        let service: LockLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [LockLogDeleteDialogComponent],
                providers: [
                    LockLogService
                ]
            })
            .overrideTemplate(LockLogDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LockLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LockLogService);
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
                        comp.confirmDelete("123");
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith("123");
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
