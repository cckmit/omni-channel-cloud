import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OperationType } from './operation-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OperationTypeService {

    private resourceUrl =  SERVER_API_URL + '/occmsinventory/api/operation-types';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(operationType: OperationType): Observable<OperationType> {
        const copy = this.convert(operationType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(operationType: OperationType): Observable<OperationType> {
        const copy = this.convert(operationType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<OperationType> {
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

    delete(id: string): Observable<Response> {
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
     * Convert a returned JSON object to OperationType.
     */
    private convertItemFromServer(json: any): OperationType {
        const entity: OperationType = Object.assign(new OperationType(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a OperationType to a JSON which can be sent to the server.
     */
    private convert(operationType: OperationType): OperationType {
        const copy: OperationType = Object.assign({}, operationType);

        copy.ts = this.dateUtils.toDate(operationType.ts);

        copy.timeCreated = this.dateUtils.toDate(operationType.timeCreated);

        copy.timeModified = this.dateUtils.toDate(operationType.timeModified);
        return copy;
    }
}
