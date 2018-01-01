/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { SoStateComponent } from '../../../../../../main/webapp/app/entities/so-state/so-state.component';
import { SoStateService } from '../../../../../../main/webapp/app/entities/so-state/so-state.service';
import { SoState } from '../../../../../../main/webapp/app/entities/so-state/so-state.model';

describe('Component Tests', () => {

    describe('SoState Management Component', () => {
        let comp: SoStateComponent;
        let fixture: ComponentFixture<SoStateComponent>;
        let service: SoStateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [SoStateComponent],
                providers: [
                    SoStateService
                ]
            })
            .overrideTemplate(SoStateComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SoStateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SoStateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SoState(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.soStates[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
