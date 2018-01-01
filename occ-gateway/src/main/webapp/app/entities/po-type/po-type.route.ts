import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PoTypeComponent } from './po-type.component';
import { PoTypeDetailComponent } from './po-type-detail.component';
import { PoTypePopupComponent } from './po-type-dialog.component';
import { PoTypeDeletePopupComponent } from './po-type-delete-dialog.component';

@Injectable()
export class PoTypeResolvePagingParams implements Resolve<any> {

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

export const poTypeRoute: Routes = [
    {
        path: 'po-type',
        component: PoTypeComponent,
        resolve: {
            'pagingParams': PoTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'po-type/:id',
        component: PoTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poTypePopupRoute: Routes = [
    {
        path: 'po-type-new',
        component: PoTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-type/:id/edit',
        component: PoTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-type/:id/delete',
        component: PoTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
