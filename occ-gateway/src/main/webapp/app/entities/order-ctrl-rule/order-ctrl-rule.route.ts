import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrderCtrlRuleComponent } from './order-ctrl-rule.component';
import { OrderCtrlRuleDetailComponent } from './order-ctrl-rule-detail.component';
import { OrderCtrlRulePopupComponent } from './order-ctrl-rule-dialog.component';
import { OrderCtrlRuleDeletePopupComponent } from './order-ctrl-rule-delete-dialog.component';

@Injectable()
export class OrderCtrlRuleResolvePagingParams implements Resolve<any> {

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

export const orderCtrlRuleRoute: Routes = [
    {
        path: 'order-ctrl-rule',
        component: OrderCtrlRuleComponent,
        resolve: {
            'pagingParams': OrderCtrlRuleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.orderCtrlRule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'order-ctrl-rule/:id',
        component: OrderCtrlRuleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.orderCtrlRule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderCtrlRulePopupRoute: Routes = [
    {
        path: 'order-ctrl-rule-new',
        component: OrderCtrlRulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.orderCtrlRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-ctrl-rule/:id/edit',
        component: OrderCtrlRulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.orderCtrlRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-ctrl-rule/:id/delete',
        component: OrderCtrlRuleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.orderCtrlRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
