import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SoStateComponent } from './so-state.component';
import { SoStateDetailComponent } from './so-state-detail.component';
import { SoStatePopupComponent } from './so-state-dialog.component';
import { SoStateDeletePopupComponent } from './so-state-delete-dialog.component';

@Injectable()
export class SoStateResolvePagingParams implements Resolve<any> {

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

export const soStateRoute: Routes = [
    {
        path: 'so-state',
        component: SoStateComponent,
        resolve: {
            'pagingParams': SoStateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soState.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'so-state/:id',
        component: SoStateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soState.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const soStatePopupRoute: Routes = [
    {
        path: 'so-state-new',
        component: SoStatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soState.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'so-state/:id/edit',
        component: SoStatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soState.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'so-state/:id/delete',
        component: SoStateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soState.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
