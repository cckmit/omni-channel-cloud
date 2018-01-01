import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PoStateComponent } from './po-state.component';
import { PoStateDetailComponent } from './po-state-detail.component';
import { PoStatePopupComponent } from './po-state-dialog.component';
import { PoStateDeletePopupComponent } from './po-state-delete-dialog.component';

@Injectable()
export class PoStateResolvePagingParams implements Resolve<any> {

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

export const poStateRoute: Routes = [
    {
        path: 'po-state',
        component: PoStateComponent,
        resolve: {
            'pagingParams': PoStateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poState.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'po-state/:id',
        component: PoStateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poState.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poStatePopupRoute: Routes = [
    {
        path: 'po-state-new',
        component: PoStatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poState.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-state/:id/edit',
        component: PoStatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poState.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-state/:id/delete',
        component: PoStateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poState.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
