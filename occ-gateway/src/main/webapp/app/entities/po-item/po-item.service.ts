import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PoItem } from './po-item.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PoItemService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/po-items';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(poItem: PoItem): Observable<PoItem> {
        const copy = this.convert(poItem);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(poItem: PoItem): Observable<PoItem> {
        const copy = this.convert(poItem);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<PoItem> {
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
     * Convert a returned JSON object to PoItem.
     */
    private convertItemFromServer(json: any): PoItem {
        const entity: PoItem = Object.assign(new PoItem(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a PoItem to a JSON which can be sent to the server.
     */
    private convert(poItem: PoItem): PoItem {
        const copy: PoItem = Object.assign({}, poItem);

        copy.ts = this.dateUtils.toDate(poItem.ts);

        copy.timeCreated = this.dateUtils.toDate(poItem.timeCreated);

        copy.timeModified = this.dateUtils.toDate(poItem.timeModified);
        return copy;
    }
}
