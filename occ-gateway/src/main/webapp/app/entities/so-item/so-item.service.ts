import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SoItem } from './so-item.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SoItemService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/so-items';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(soItem: SoItem): Observable<SoItem> {
        const copy = this.convert(soItem);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(soItem: SoItem): Observable<SoItem> {
        const copy = this.convert(soItem);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<SoItem> {
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
     * Convert a returned JSON object to SoItem.
     */
    private convertItemFromServer(json: any): SoItem {
        const entity: SoItem = Object.assign(new SoItem(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a SoItem to a JSON which can be sent to the server.
     */
    private convert(soItem: SoItem): SoItem {
        const copy: SoItem = Object.assign({}, soItem);

        copy.ts = this.dateUtils.toDate(soItem.ts);

        copy.timeCreated = this.dateUtils.toDate(soItem.timeCreated);

        copy.timeModified = this.dateUtils.toDate(soItem.timeModified);
        return copy;
    }
}
