import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SoItemComponent } from './so-item.component';
import { SoItemDetailComponent } from './so-item-detail.component';
import { SoItemPopupComponent } from './so-item-dialog.component';
import { SoItemDeletePopupComponent } from './so-item-delete-dialog.component';

@Injectable()
export class SoItemResolvePagingParams implements Resolve<any> {

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

export const soItemRoute: Routes = [
    {
        path: 'so-item',
        component: SoItemComponent,
        resolve: {
            'pagingParams': SoItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'so-item/:id',
        component: SoItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const soItemPopupRoute: Routes = [
    {
        path: 'so-item-new',
        component: SoItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'so-item/:id/edit',
        component: SoItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'so-item/:id/delete',
        component: SoItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.soItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
