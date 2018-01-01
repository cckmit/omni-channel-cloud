/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { PoStateDialogComponent } from '../../../../../../main/webapp/app/entities/po-state/po-state-dialog.component';
import { PoStateService } from '../../../../../../main/webapp/app/entities/po-state/po-state.service';
import { PoState } from '../../../../../../main/webapp/app/entities/po-state/po-state.model';

describe('Component Tests', () => {

    describe('PoState Management Dialog Component', () => {
        let comp: PoStateDialogComponent;
        let fixture: ComponentFixture<PoStateDialogComponent>;
        let service: PoStateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoStateDialogComponent],
                providers: [
                    PoStateService
                ]
            })
            .overrideTemplate(PoStateDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoStateDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoStateService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoState(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.poState = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poStateListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoState();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.poState = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poStateListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
