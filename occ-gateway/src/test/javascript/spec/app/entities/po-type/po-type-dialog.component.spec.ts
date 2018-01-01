/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { PoTypeDialogComponent } from '../../../../../../main/webapp/app/entities/po-type/po-type-dialog.component';
import { PoTypeService } from '../../../../../../main/webapp/app/entities/po-type/po-type.service';
import { PoType } from '../../../../../../main/webapp/app/entities/po-type/po-type.model';

describe('Component Tests', () => {

    describe('PoType Management Dialog Component', () => {
        let comp: PoTypeDialogComponent;
        let fixture: ComponentFixture<PoTypeDialogComponent>;
        let service: PoTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoTypeDialogComponent],
                providers: [
                    PoTypeService
                ]
            })
            .overrideTemplate(PoTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.poType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.poType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'poTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
