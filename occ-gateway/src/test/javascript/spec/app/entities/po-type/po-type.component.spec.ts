/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { PoTypeComponent } from '../../../../../../main/webapp/app/entities/po-type/po-type.component';
import { PoTypeService } from '../../../../../../main/webapp/app/entities/po-type/po-type.service';
import { PoType } from '../../../../../../main/webapp/app/entities/po-type/po-type.model';

describe('Component Tests', () => {

    describe('PoType Management Component', () => {
        let comp: PoTypeComponent;
        let fixture: ComponentFixture<PoTypeComponent>;
        let service: PoTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoTypeComponent],
                providers: [
                    PoTypeService
                ]
            })
            .overrideTemplate(PoTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PoType("123")],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.poTypes[0]).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
