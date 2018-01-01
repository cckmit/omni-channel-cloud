import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CustomerAccountComponent } from './customer-account.component';
import { CustomerAccountDetailComponent } from './customer-account-detail.component';
import { CustomerAccountPopupComponent } from './customer-account-dialog.component';
import { CustomerAccountDeletePopupComponent } from './customer-account-delete-dialog.component';

@Injectable()
export class CustomerAccountResolvePagingParams implements Resolve<any> {

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

export const customerAccountRoute: Routes = [
    {
        path: 'customer-account',
        component: CustomerAccountComponent,
        resolve: {
            'pagingParams': CustomerAccountResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.customerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-account/:id',
        component: CustomerAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.customerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerAccountPopupRoute: Routes = [
    {
        path: 'customer-account-new',
        component: CustomerAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.customerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-account/:id/edit',
        component: CustomerAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.customerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-account/:id/delete',
        component: CustomerAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.customerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
