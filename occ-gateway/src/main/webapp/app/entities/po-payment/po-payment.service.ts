import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PoPayment } from './po-payment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PoPaymentService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/po-payments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(poPayment: PoPayment): Observable<PoPayment> {
        const copy = this.convert(poPayment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(poPayment: PoPayment): Observable<PoPayment> {
        const copy = this.convert(poPayment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<PoPayment> {
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
     * Convert a returned JSON object to PoPayment.
     */
    private convertItemFromServer(json: any): PoPayment {
        const entity: PoPayment = Object.assign(new PoPayment(), json);
        entity.timePaid = this.dateUtils
            .convertDateTimeFromServer(json.timePaid);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a PoPayment to a JSON which can be sent to the server.
     */
    private convert(poPayment: PoPayment): PoPayment {
        const copy: PoPayment = Object.assign({}, poPayment);

        copy.timePaid = this.dateUtils.toDate(poPayment.timePaid);

        copy.ts = this.dateUtils.toDate(poPayment.ts);

        copy.timeCreated = this.dateUtils.toDate(poPayment.timeCreated);

        copy.timeModified = this.dateUtils.toDate(poPayment.timeModified);
        return copy;
    }
}
