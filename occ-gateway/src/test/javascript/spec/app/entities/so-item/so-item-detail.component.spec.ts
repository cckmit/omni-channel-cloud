/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { SoItemDetailComponent } from '../../../../../../main/webapp/app/entities/so-item/so-item-detail.component';
import { SoItemService } from '../../../../../../main/webapp/app/entities/so-item/so-item.service';
import { SoItem } from '../../../../../../main/webapp/app/entities/so-item/so-item.model';

describe('Component Tests', () => {

    describe('SoItem Management Detail Component', () => {
        let comp: SoItemDetailComponent;
        let fixture: ComponentFixture<SoItemDetailComponent>;
        let service: SoItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoItemDetailComponent],
                providers: [
                    SoItemService
                ]
            })
            .overrideTemplate(SoItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SoItem("123")));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith("123");
                expect(comp.soItem).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
