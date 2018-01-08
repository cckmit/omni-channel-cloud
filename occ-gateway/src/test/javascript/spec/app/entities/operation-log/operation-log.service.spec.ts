/* tslint:disable max-line-length */
import { TestBed, async } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { ConnectionBackend, RequestOptions, BaseRequestOptions, Http, Response, ResponseOptions } from '@angular/http';
import { JhiDateUtils } from 'ng-jhipster';

import { OperationLogService } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.service';
import { OperationLog } from '../../../../../../main/webapp/app/entities/operation-log/operation-log.model';
import { SERVER_API_URL } from '../../../../../../main/webapp/app/app.constants';

describe('Service Tests', () => {

    describe('OperationLog Service', () => {
        let service: OperationLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                providers: [
                    {
                        provide: ConnectionBackend,
                        useClass: MockBackend
                    },
                    {
                        provide: RequestOptions,
                        useClass: BaseRequestOptions
                    },
                    Http,
                    JhiDateUtils,
                    OperationLogService
                ]
            });

            service = TestBed.get(OperationLogService);

            this.backend = TestBed.get(ConnectionBackend) as MockBackend;
            this.backend.connections.subscribe((connection: any) => {
                this.lastConnection = connection;
            });
        }));

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.find("123").subscribe(() => {});

                expect(this.lastConnection).toBeDefined();

                const resourceUrl = SERVER_API_URL + '/occmsinventory/api/operation-logs';
                expect(this.lastConnection.request.url).toEqual(resourceUrl + '/' + "123");
            });
            it('should return OperationLog', () => {

                let entity: OperationLog;
                service.find("123").subscribe((_entity: OperationLog) => {
                    entity = _entity;
                });

                this.lastConnection.mockRespond(new Response(new ResponseOptions({
                    body: JSON.stringify({id: "123"}),
                })));

                expect(entity).toBeDefined();
                expect(entity.id).toEqual("123");
            });

            it('should propagate not found response', () => {

                let error: any;
                service.find("123").subscribe(null, (_error: any) => {
                    error = _error;
                });

                this.lastConnection.mockError(new Response(new ResponseOptions({
                    status: 404,
                })));

                expect(error).toBeDefined();
                expect(error.status).toEqual(404);
            });
        });
    });

});
