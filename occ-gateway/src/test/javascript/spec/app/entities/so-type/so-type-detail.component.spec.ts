/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { SoTypeDetailComponent } from '../../../../../../main/webapp/app/entities/so-type/so-type-detail.component';
import { SoTypeService } from '../../../../../../main/webapp/app/entities/so-type/so-type.service';
import { SoType } from '../../../../../../main/webapp/app/entities/so-type/so-type.model';

describe('Component Tests', () => {

    describe('SoType Management Detail Component', () => {
        let comp: SoTypeDetailComponent;
        let fixture: ComponentFixture<SoTypeDetailComponent>;
        let service: SoTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoTypeDetailComponent],
                providers: [
                    SoTypeService
                ]
            })
            .overrideTemplate(SoTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SoType("123")));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith("123");
                expect(comp.soType).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
