import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SaleOrderComponent } from './sale-order.component';
import { SaleOrderDetailComponent } from './sale-order-detail.component';
import { SaleOrderPopupComponent } from './sale-order-dialog.component';
import { SaleOrderDeletePopupComponent } from './sale-order-delete-dialog.component';

@Injectable()
export class SaleOrderResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const saleOrderRoute: Routes = [
    {
        path: 'sale-order',
        component: SaleOrderComponent,
        resolve: {
            'pagingParams': SaleOrderResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sale-order/:id',
        component: SaleOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleOrderPopupRoute: Routes = [
    {
        path: 'sale-order-new',
        component: SaleOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sale-order/:id/edit',
        component: SaleOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sale-order/:id/delete',
        component: SaleOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
