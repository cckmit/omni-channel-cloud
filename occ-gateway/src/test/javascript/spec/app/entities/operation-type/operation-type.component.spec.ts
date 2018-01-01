/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { OccGatewayTestModule } from '../../../test.module';
import { OperationTypeComponent } from '../../../../../../main/webapp/app/entities/operation-type/operation-type.component';
import { OperationTypeService } from '../../../../../../main/webapp/app/entities/operation-type/operation-type.service';
import { OperationType } from '../../../../../../main/webapp/app/entities/operation-type/operation-type.model';

describe('Component Tests', () => {

    describe('OperationType Management Component', () => {
        let comp: OperationTypeComponent;
        let fixture: ComponentFixture<OperationTypeComponent>;
        let service: OperationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OccGatewayTestModule],
                declarations: [OperationTypeComponent],
                providers: [
                    OperationTypeService
                ]
            })
            .overrideTemplate(OperationTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new OperationType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.operationTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
