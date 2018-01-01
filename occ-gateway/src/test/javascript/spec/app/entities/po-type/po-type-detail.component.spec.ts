/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { PoTypeDetailComponent } from '../../../../../../main/webapp/app/entities/po-type/po-type-detail.component';
import { PoTypeService } from '../../../../../../main/webapp/app/entities/po-type/po-type.service';
import { PoType } from '../../../../../../main/webapp/app/entities/po-type/po-type.model';

describe('Component Tests', () => {

    describe('PoType Management Detail Component', () => {
        let comp: PoTypeDetailComponent;
        let fixture: ComponentFixture<PoTypeDetailComponent>;
        let service: PoTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoTypeDetailComponent],
                providers: [
                    PoTypeService
                ]
            })
            .overrideTemplate(PoTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PoType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.poType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
