/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { SoStateDialogComponent } from '../../../../../../main/webapp/app/entities/so-state/so-state-dialog.component';
import { SoStateService } from '../../../../../../main/webapp/app/entities/so-state/so-state.service';
import { SoState } from '../../../../../../main/webapp/app/entities/so-state/so-state.model';

describe('Component Tests', () => {

    describe('SoState Management Dialog Component', () => {
        let comp: SoStateDialogComponent;
        let fixture: ComponentFixture<SoStateDialogComponent>;
        let service: SoStateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoStateDialogComponent],
                providers: [
                    SoStateService
                ]
            })
            .overrideTemplate(SoStateDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoStateDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoStateService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SoState("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.soState = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'soStateListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SoState();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.soState = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'soStateListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
