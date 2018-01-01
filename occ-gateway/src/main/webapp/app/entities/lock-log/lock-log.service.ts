import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LockLog } from './lock-log.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LockLogService {

    private resourceUrl =  SERVER_API_URL + '/occmsinventory/api/lock-logs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(lockLog: LockLog): Observable<LockLog> {
        const copy = this.convert(lockLog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(lockLog: LockLog): Observable<LockLog> {
        const copy = this.convert(lockLog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LockLog> {
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
     * Convert a returned JSON object to LockLog.
     */
    private convertItemFromServer(json: any): LockLog {
        const entity: LockLog = Object.assign(new LockLog(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a LockLog to a JSON which can be sent to the server.
     */
    private convert(lockLog: LockLog): LockLog {
        const copy: LockLog = Object.assign({}, lockLog);

        copy.ts = this.dateUtils.toDate(lockLog.ts);

        copy.timeCreated = this.dateUtils.toDate(lockLog.timeCreated);

        copy.timeModified = this.dateUtils.toDate(lockLog.timeModified);
        return copy;
    }
}
