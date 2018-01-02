import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Customer } from './customer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CustomerService {

    private resourceUrl =  SERVER_API_URL + '/occmscustomer/api/customers';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(customer: Customer): Observable<Customer> {
        const copy = this.convert(customer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(customer: Customer): Observable<Customer> {
        const copy = this.convert(customer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Customer> {
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
     * Convert a returned JSON object to Customer.
     */
    private convertItemFromServer(json: any): Customer {
        const entity: Customer = Object.assign(new Customer(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a Customer to a JSON which can be sent to the server.
     */
    private convert(customer: Customer): Customer {
        const copy: Customer = Object.assign({}, customer);

        copy.ts = this.dateUtils.toDate(customer.ts);

        copy.timeCreated = this.dateUtils.toDate(customer.timeCreated);

        copy.timeModified = this.dateUtils.toDate(customer.timeModified);
        return copy;
    }
}
