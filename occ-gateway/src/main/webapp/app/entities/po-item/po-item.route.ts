import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PoItemComponent } from './po-item.component';
import { PoItemDetailComponent } from './po-item-detail.component';
import { PoItemPopupComponent } from './po-item-dialog.component';
import { PoItemDeletePopupComponent } from './po-item-delete-dialog.component';

@Injectable()
export class PoItemResolvePagingParams implements Resolve<any> {

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

export const poItemRoute: Routes = [
    {
        path: 'po-item',
        component: PoItemComponent,
        resolve: {
            'pagingParams': PoItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'po-item/:id',
        component: PoItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poItemPopupRoute: Routes = [
    {
        path: 'po-item-new',
        component: PoItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-item/:id/edit',
        component: PoItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'po-item/:id/delete',
        component: PoItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.poItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
