import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SaleOrder } from './sale-order.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SaleOrderService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/sale-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(saleOrder: SaleOrder): Observable<SaleOrder> {
        const copy = this.convert(saleOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(saleOrder: SaleOrder): Observable<SaleOrder> {
        const copy = this.convert(saleOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<SaleOrder> {
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
     * Convert a returned JSON object to SaleOrder.
     */
    private convertItemFromServer(json: any): SaleOrder {
        const entity: SaleOrder = Object.assign(new SaleOrder(), json);
        entity.orderDate = this.dateUtils
            .convertDateTimeFromServer(json.orderDate);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a SaleOrder to a JSON which can be sent to the server.
     */
    private convert(saleOrder: SaleOrder): SaleOrder {
        const copy: SaleOrder = Object.assign({}, saleOrder);

        copy.orderDate = this.dateUtils.toDate(saleOrder.orderDate);

        copy.ts = this.dateUtils.toDate(saleOrder.ts);

        copy.timeCreated = this.dateUtils.toDate(saleOrder.timeCreated);

        copy.timeModified = this.dateUtils.toDate(saleOrder.timeModified);
        return copy;
    }
}
