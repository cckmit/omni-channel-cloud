import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PoState } from './po-state.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PoStateService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/po-states';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(poState: PoState): Observable<PoState> {
        const copy = this.convert(poState);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(poState: PoState): Observable<PoState> {
        const copy = this.convert(poState);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PoState> {
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
     * Convert a returned JSON object to PoState.
     */
    private convertItemFromServer(json: any): PoState {
        const entity: PoState = Object.assign(new PoState(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a PoState to a JSON which can be sent to the server.
     */
    private convert(poState: PoState): PoState {
        const copy: PoState = Object.assign({}, poState);

        copy.ts = this.dateUtils.toDate(poState.ts);

        copy.timeCreated = this.dateUtils.toDate(poState.timeCreated);

        copy.timeModified = this.dateUtils.toDate(poState.timeModified);
        return copy;
    }
}
