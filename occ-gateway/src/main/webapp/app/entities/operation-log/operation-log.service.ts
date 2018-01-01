import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OperationLog } from './operation-log.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OperationLogService {

    private resourceUrl =  SERVER_API_URL + '/occmsinventory/api/operation-logs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(operationLog: OperationLog): Observable<OperationLog> {
        const copy = this.convert(operationLog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(operationLog: OperationLog): Observable<OperationLog> {
        const copy = this.convert(operationLog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OperationLog> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to OperationLog.
     */
    private convertItemFromServer(json: any): OperationLog {
        const entity: OperationLog = Object.assign(new OperationLog(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a OperationLog to a JSON which can be sent to the server.
     */
    private convert(operationLog: OperationLog): OperationLog {
        const copy: OperationLog = Object.assign({}, operationLog);

        copy.ts = this.dateUtils.toDate(operationLog.ts);

        copy.timeCreated = this.dateUtils.toDate(operationLog.timeCreated);

        copy.timeModified = this.dateUtils.toDate(operationLog.timeModified);
        return copy;
    }
}
