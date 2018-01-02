import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PoType } from './po-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PoTypeService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/po-types';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(poType: PoType): Observable<PoType> {
        const copy = this.convert(poType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(poType: PoType): Observable<PoType> {
        const copy = this.convert(poType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<PoType> {
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
     * Convert a returned JSON object to PoType.
     */
    private convertItemFromServer(json: any): PoType {
        const entity: PoType = Object.assign(new PoType(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a PoType to a JSON which can be sent to the server.
     */
    private convert(poType: PoType): PoType {
        const copy: PoType = Object.assign({}, poType);

        copy.ts = this.dateUtils.toDate(poType.ts);

        copy.timeCreated = this.dateUtils.toDate(poType.timeCreated);

        copy.timeModified = this.dateUtils.toDate(poType.timeModified);
        return copy;
    }
}
