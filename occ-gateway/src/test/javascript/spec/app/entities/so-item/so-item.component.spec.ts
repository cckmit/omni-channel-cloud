/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { SoItemComponent } from '../../../../../../main/webapp/app/entities/so-item/so-item.component';
import { SoItemService } from '../../../../../../main/webapp/app/entities/so-item/so-item.service';
import { SoItem } from '../../../../../../main/webapp/app/entities/so-item/so-item.model';

describe('Component Tests', () => {

    describe('SoItem Management Component', () => {
        let comp: SoItemComponent;
        let fixture: ComponentFixture<SoItemComponent>;
        let service: SoItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoItemComponent],
                providers: [
                    SoItemService
                ]
            })
            .overrideTemplate(SoItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SoItem("123")],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.soItems[0]).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
