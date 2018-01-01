import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CustomerAccount } from './customer-account.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CustomerAccountService {

    private resourceUrl =  SERVER_API_URL + '/occmscustomer/api/customer-accounts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(customerAccount: CustomerAccount): Observable<CustomerAccount> {
        const copy = this.convert(customerAccount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(customerAccount: CustomerAccount): Observable<CustomerAccount> {
        const copy = this.convert(customerAccount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CustomerAccount> {
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
     * Convert a returned JSON object to CustomerAccount.
     */
    private convertItemFromServer(json: any): CustomerAccount {
        const entity: CustomerAccount = Object.assign(new CustomerAccount(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a CustomerAccount to a JSON which can be sent to the server.
     */
    private convert(customerAccount: CustomerAccount): CustomerAccount {
        const copy: CustomerAccount = Object.assign({}, customerAccount);

        copy.ts = this.dateUtils.toDate(customerAccount.ts);

        copy.timeCreated = this.dateUtils.toDate(customerAccount.timeCreated);

        copy.timeModified = this.dateUtils.toDate(customerAccount.timeModified);
        return copy;
    }
}
