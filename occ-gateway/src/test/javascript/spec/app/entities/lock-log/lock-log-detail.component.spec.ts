/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { OccGatewayTestModule } from '../../../test.module';
import { LockLogDetailComponent } from '../../../../../../main/webapp/app/entities/lock-log/lock-log-detail.component';
import { LockLogService } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.service';
import { LockLog } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.model';

describe('Component Tests', () => {

    describe('LockLog Management Detail Component', () => {
        let comp: LockLogDetailComponent;
        let fixture: ComponentFixture<LockLogDetailComponent>;
        let service: LockLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [LockLogDetailComponent],
                providers: [
                    LockLogService
                ]
            })
            .overrideTemplate(LockLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LockLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LockLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new LockLog(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lockLog).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
