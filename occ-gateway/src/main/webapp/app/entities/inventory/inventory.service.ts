import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Inventory } from './inventory.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InventoryService {

    private resourceUrl =  SERVER_API_URL + '/occmsinventory/api/inventories';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(inventory: Inventory): Observable<Inventory> {
        const copy = this.convert(inventory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(inventory: Inventory): Observable<Inventory> {
        const copy = this.convert(inventory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Inventory> {
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
     * Convert a returned JSON object to Inventory.
     */
    private convertItemFromServer(json: any): Inventory {
        const entity: Inventory = Object.assign(new Inventory(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a Inventory to a JSON which can be sent to the server.
     */
    private convert(inventory: Inventory): Inventory {
        const copy: Inventory = Object.assign({}, inventory);

        copy.ts = this.dateUtils.toDate(inventory.ts);

        copy.timeCreated = this.dateUtils.toDate(inventory.timeCreated);

        copy.timeModified = this.dateUtils.toDate(inventory.timeModified);
        return copy;
    }
}
