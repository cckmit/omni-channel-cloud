/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { PoItemComponent } from '../../../../../../main/webapp/app/entities/po-item/po-item.component';
import { PoItemService } from '../../../../../../main/webapp/app/entities/po-item/po-item.service';
import { PoItem } from '../../../../../../main/webapp/app/entities/po-item/po-item.model';

describe('Component Tests', () => {

    describe('PoItem Management Component', () => {
        let comp: PoItemComponent;
        let fixture: ComponentFixture<PoItemComponent>;
        let service: PoItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoItemComponent],
                providers: [
                    PoItemService
                ]
            })
            .overrideTemplate(PoItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PoItem(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.poItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
