import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PoPaymentComponent } from './po-payment.component';
import { PoPaymentDetailComponent } from './po-payment-detail.component';
import { PoPaymentPopupComponent } from './po-payment-dialog.component';
import { PoPaymentDeletePopupComponent } from './po-payment-delete-dialog.component';

@Injectable()
export class PoPaymentResolvePagingParams implements Resolve<any> {

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

export const poPaymentRoute: Routes = [
    {
        path: 'po-payment',
        component: PoPaymentComponent,
        resolve: {
            'pagingParams': PoPaymentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poPayment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'po-payment/:id',
        component: PoPaymentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poPayment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poPaymentPopupRoute: Routes = [
    {
        path: 'po-payment-new',
        component: PoPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poPayment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-payment/:id/edit',
        component: PoPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poPayment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-payment/:id/delete',
        component: PoPaymentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poPayment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
