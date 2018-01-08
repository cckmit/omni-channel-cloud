/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { OperationLogDetailComponent } from '../../../../../../main/webapp/app/entities/operation-log/operation-log-detail.component';
import { OperationLogService } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.service';
import { OperationLog } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.model';

describe('Component Tests', () => {

    describe('OperationLog Management Detail Component', () => {
        let comp: OperationLogDetailComponent;
        let fixture: ComponentFixture<OperationLogDetailComponent>;
        let service: OperationLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OperationLogDetailComponent],
                providers: [
                    OperationLogService
                ]
            })
            .overrideTemplate(OperationLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new OperationLog("123")));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith("123");
                expect(comp.operationLog).toEqual(jasmine.objectContaining({id: "123"}));
            });
        });
    });

});
