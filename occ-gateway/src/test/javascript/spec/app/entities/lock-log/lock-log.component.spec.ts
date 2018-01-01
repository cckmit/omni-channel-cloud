/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { LockLogComponent } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.component';
import { LockLogService } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.service';
import { LockLog } from '../../../../../../main/webapp/app/entities/lock-log/lock-log.model';

describe('Component Tests', () => {

    describe('LockLog Management Component', () => {
        let comp: LockLogComponent;
        let fixture: ComponentFixture<LockLogComponent>;
        let service: LockLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [LockLogComponent],
                providers: [
                    LockLogService
                ]
            })
            .overrideTemplate(LockLogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LockLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LockLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new LockLog(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lockLogs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
