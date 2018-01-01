import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SoState } from './so-state.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SoStateService {

    private resourceUrl =  SERVER_API_URL + '/occmsorder/api/so-states';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(soState: SoState): Observable<SoState> {
        const copy = this.convert(soState);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(soState: SoState): Observable<SoState> {
        const copy = this.convert(soState);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SoState> {
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
     * Convert a returned JSON object to SoState.
     */
    private convertItemFromServer(json: any): SoState {
        const entity: SoState = Object.assign(new SoState(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a SoState to a JSON which can be sent to the server.
     */
    private convert(soState: SoState): SoState {
        const copy: SoState = Object.assign({}, soState);

        copy.ts = this.dateUtils.toDate(soState.ts);

        copy.timeCreated = this.dateUtils.toDate(soState.timeCreated);

        copy.timeModified = this.dateUtils.toDate(soState.timeModified);
        return copy;
    }
}
