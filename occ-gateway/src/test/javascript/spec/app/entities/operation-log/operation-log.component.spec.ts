/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { OperationLogComponent } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.component';
import { OperationLogService } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.service';
import { OperationLog } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.model';

describe('Component Tests', () => {

    describe('OperationLog Management Component', () => {
        let comp: OperationLogComponent;
        let fixture: ComponentFixture<OperationLogComponent>;
        let service: OperationLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OperationLogComponent],
                providers: [
                    OperationLogService
                ]
            })
            .overrideTemplate(OperationLogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new OperationLog(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.operationLogs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
