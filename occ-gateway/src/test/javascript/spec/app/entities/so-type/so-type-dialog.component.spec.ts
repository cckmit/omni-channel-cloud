/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { SoTypeDialogComponent } from '../../../../../../main/webapp/app/entities/so-type/so-type-dialog.component';
import { SoTypeService } from '../../../../../../main/webapp/app/entities/so-type/so-type.service';
import { SoType } from '../../../../../../main/webapp/app/entities/so-type/so-type.model';

describe('Component Tests', () => {

    describe('SoType Management Dialog Component', () => {
        let comp: SoTypeDialogComponent;
        let fixture: ComponentFixture<SoTypeDialogComponent>;
        let service: SoTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoTypeDialogComponent],
                providers: [
                    SoTypeService
                ]
            })
            .overrideTemplate(SoTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SoType("123");
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.soType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'soTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SoType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.soType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'soTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
