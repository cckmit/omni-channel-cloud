/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { PoItemDetailComponent } from '../../../../../../main/webapp/app/entities/po-item/po-item-detail.component';
import { PoItemService } from '../../../../../../main/webapp/app/entities/po-item/po-item.service';
import { PoItem } from '../../../../../../main/webapp/app/entities/po-item/po-item.model';

describe('Component Tests', () => {

    describe('PoItem Management Detail Component', () => {
        let comp: PoItemDetailComponent;
        let fixture: ComponentFixture<PoItemDetailComponent>;
        let service: PoItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoItemDetailComponent],
                providers: [
                    PoItemService
                ]
            })
            .overrideTemplate(PoItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PoItem(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.poItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
