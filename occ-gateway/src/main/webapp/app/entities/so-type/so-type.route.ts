import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SoTypeComponent } from './so-type.component';
import { SoTypeDetailComponent } from './so-type-detail.component';
import { SoTypePopupComponent } from './so-type-dialog.component';
import { SoTypeDeletePopupComponent } from './so-type-delete-dialog.component';

@Injectable()
export class SoTypeResolvePagingParams implements Resolve<any> {

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

export const soTypeRoute: Routes = [
    {
        path: 'so-type',
        component: SoTypeComponent,
        resolve: {
            'pagingParams': SoTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'so-type/:id',
        component: SoTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const soTypePopupRoute: Routes = [
    {
        path: 'so-type-new',
        component: SoTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'so-type/:id/edit',
        component: SoTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'so-type/:id/delete',
        component: SoTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
