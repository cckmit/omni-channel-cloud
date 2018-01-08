/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { SoStateDetailComponent } from '../../../../../../main/webapp/app/entities/so-state/so-state-detail.component';
import { SoStateService } from '../../../../../../main/webapp/app/entities/so-state/so-state.service';
import { SoState } from '../../../../../../main/webapp/app/entities/so-state/so-state.model';

describe('Component Tests', () => {

    describe('SoState Management Detail Component', () => {
        let comp: SoStateDetailComponent;
        let fixture: ComponentFixture<SoStateDetailComponent>;
        let service: SoStateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoStateDetailComponent],
                providers: [
                    SoStateService
                ]
            })
            .overrideTemplate(SoStateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoStateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoStateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SoState("123")));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith("123");
                expect(comp.soState).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
