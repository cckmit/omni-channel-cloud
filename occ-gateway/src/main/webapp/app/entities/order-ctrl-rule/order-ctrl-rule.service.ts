import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OrderCtrlRule } from './order-ctrl-rule.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OrderCtrlRuleService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/order-ctrl-rules';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(orderCtrlRule: OrderCtrlRule): Observable<OrderCtrlRule> {
        const copy = this.convert(orderCtrlRule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(orderCtrlRule: OrderCtrlRule): Observable<OrderCtrlRule> {
        const copy = this.convert(orderCtrlRule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OrderCtrlRule> {
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
     * Convert a returned JSON object to OrderCtrlRule.
     */
    private convertItemFromServer(json: any): OrderCtrlRule {
        const entity: OrderCtrlRule = Object.assign(new OrderCtrlRule(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a OrderCtrlRule to a JSON which can be sent to the server.
     */
    private convert(orderCtrlRule: OrderCtrlRule): OrderCtrlRule {
        const copy: OrderCtrlRule = Object.assign({}, orderCtrlRule);

        copy.ts = this.dateUtils.toDate(orderCtrlRule.ts);

        copy.timeCreated = this.dateUtils.toDate(orderCtrlRule.timeCreated);

        copy.timeModified = this.dateUtils.toDate(orderCtrlRule.timeModified);
        return copy;
    }
}
