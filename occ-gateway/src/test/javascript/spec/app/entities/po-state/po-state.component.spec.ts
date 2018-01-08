/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { PoStateComponent } from '../../../../../../main/webapp/app/entities/po-state/po-state.component';
import { PoStateService } from '../../../../../../main/webapp/app/entities/po-state/po-state.service';
import { PoState } from '../../../../../../main/webapp/app/entities/po-state/po-state.model';

describe('Component Tests', () => {

    describe('PoState Management Component', () => {
        let comp: PoStateComponent;
        let fixture: ComponentFixture<PoStateComponent>;
        let service: PoStateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [PoStateComponent],
                providers: [
                    PoStateService
                ]
            })
            .overrideTemplate(PoStateComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoStateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoStateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PoState("123")],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.poStates[0]).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
