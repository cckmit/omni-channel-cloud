/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { PoStateDetailComponent } from '../../../../../../main/webapp/app/entities/po-state/po-state-detail.component';
import { PoStateService } from '../../../../../../main/webapp/app/entities/po-state/po-state.service';
import { PoState } from '../../../../../../main/webapp/app/entities/po-state/po-state.model';

describe('Component Tests', () => {

    describe('PoState Management Detail Component', () => {
        let comp: PoStateDetailComponent;
        let fixture: ComponentFixture<PoStateDetailComponent>;
        let service: PoStateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoStateDetailComponent],
                providers: [
                    PoStateService
                ]
            })
            .overrideTemplate(PoStateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoStateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoStateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PoState(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.poState).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
