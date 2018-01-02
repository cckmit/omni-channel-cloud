import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SoType } from './so-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SoTypeService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/so-types';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(soType: SoType): Observable<SoType> {
        const copy = this.convert(soType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(soType: SoType): Observable<SoType> {
        const copy = this.convert(soType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<SoType> {
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
     * Convert a returned JSON object to SoType.
     */
    private convertItemFromServer(json: any): SoType {
        const entity: SoType = Object.assign(new SoType(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a SoType to a JSON which can be sent to the server.
     */
    private convert(soType: SoType): SoType {
        const copy: SoType = Object.assign({}, soType);

        copy.ts = this.dateUtils.toDate(soType.ts);

        copy.timeCreated = this.dateUtils.toDate(soType.timeCreated);

        copy.timeModified = this.dateUtils.toDate(soType.timeModified);
        return copy;
    }
}
