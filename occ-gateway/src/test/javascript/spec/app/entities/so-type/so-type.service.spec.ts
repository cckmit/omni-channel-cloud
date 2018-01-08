/* tslint:disable max-line-length */
import { TestBed, async } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { ConnectionBackend, RequestOptions, BaseRequestOptions, Http, Response, ResponseOptions } from '@angular/http';
import { JhiDateUtils } from 'ng-jhipster';

import { SoTypeService } from '../../../../../../main/webapp/app/entities/so-type/so-type.service';
import { SoType } from '../../../../../../main/webapp/app/entities/so-type/so-type.model';
import { SERVER_API_URL } from '../../../../../../main/webapp/app/app.constants';

describe('Service Tests', () => {

    describe('SoType Service', () => {
        let service: SoTypeService;

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
                    SoTypeService
                ]
            });

            service = TestBed.get(SoTypeService);

            this.backend = TestBed.get(ConnectionBackend) as MockBackend;
            this.backend.connections.subscribe((connection: any) => {
                this.lastConnection = connection;
            });
        }));

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.find("123").subscribe(() => {});

                expect(this.lastConnection).toBeDefined();

                const resourceUrl = SERVER_API_URL + '/occmsorder/api/so-types';
                expect(this.lastConnection.request.url).toEqual(resourceUrl + '/' + "123");
            });
            it('should return SoType', () => {

                let entity: SoType;
                service.find("123").subscribe((_entity: SoType) => {
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
