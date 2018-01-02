import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ProductCategory } from './product-category.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProductCategoryService {

    private resourceUrl =  SERVER_API_URL + '/occmsproduct/api/product-categories';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(productCategory: ProductCategory): Observable<ProductCategory> {
        const copy = this.convert(productCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(productCategory: ProductCategory): Observable<ProductCategory> {
        const copy = this.convert(productCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<ProductCategory> {
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
     * Convert a returned JSON object to ProductCategory.
     */
    private convertItemFromServer(json: any): ProductCategory {
        const entity: ProductCategory = Object.assign(new ProductCategory(), json);
        entity.ts = this.dateUtils
            .convertDateTimeFromServer(json.ts);
        entity.timeCreated = this.dateUtils
            .convertDateTimeFromServer(json.timeCreated);
        entity.timeModified = this.dateUtils
            .convertDateTimeFromServer(json.timeModified);
        return entity;
    }

    /**
     * Convert a ProductCategory to a JSON which can be sent to the server.
     */
    private convert(productCategory: ProductCategory): ProductCategory {
        const copy: ProductCategory = Object.assign({}, productCategory);

        copy.ts = this.dateUtils.toDate(productCategory.ts);

        copy.timeCreated = this.dateUtils.toDate(productCategory.timeCreated);

        copy.timeModified = this.dateUtils.toDate(productCategory.timeModified);
        return copy;
    }
}
