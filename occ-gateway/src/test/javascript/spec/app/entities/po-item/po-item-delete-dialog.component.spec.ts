/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OccGatewayTestModule } from '../../../test.module';
import { PoItemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/po-item/po-item-delete-dialog.component';
import { PoItemService } from '../../../../../../main/webapp/app/entities/po-item/po-item.service';

describe('Component Tests', () => {

    describe('PoItem Management Delete Component', () => {
        let comp: PoItemDeleteDialogComponent;
        let fixture: ComponentFixture<PoItemDeleteDialogComponent>;
        let service: PoItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoItemDeleteDialogComponent],
                providers: [
                    PoItemService
                ]
            })
            .overrideTemplate(PoItemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoItemService);
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
