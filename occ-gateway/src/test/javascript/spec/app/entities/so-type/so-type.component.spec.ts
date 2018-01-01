/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { SoTypeComponent } from '../../../../../../main/webapp/app/entities/so-type/so-type.component';
import { SoTypeService } from '../../../../../../main/webapp/app/entities/so-type/so-type.service';
import { SoType } from '../../../../../../main/webapp/app/entities/so-type/so-type.model';

describe('Component Tests', () => {

    describe('SoType Management Component', () => {
        let comp: SoTypeComponent;
        let fixture: ComponentFixture<SoTypeComponent>;
        let service: SoTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoTypeComponent],
                providers: [
                    SoTypeService
                ]
            })
            .overrideTemplate(SoTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SoType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.soTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
