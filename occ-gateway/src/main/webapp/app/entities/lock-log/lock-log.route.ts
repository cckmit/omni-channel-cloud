import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LockLogComponent } from './lock-log.component';
import { LockLogDetailComponent } from './lock-log-detail.component';
import { LockLogPopupComponent } from './lock-log-dialog.component';
import { LockLogDeletePopupComponent } from './lock-log-delete-dialog.component';

@Injectable()
export class LockLogResolvePagingParams implements Resolve<any> {

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

export const lockLogRoute: Routes = [
    {
        path: 'lock-log',
        component: LockLogComponent,
        resolve: {
            'pagingParams': LockLogResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.lockLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lock-log/:id',
        component: LockLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.lockLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lockLogPopupRoute: Routes = [
    {
        path: 'lock-log-new',
        component: LockLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.lockLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lock-log/:id/edit',
        component: LockLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.lockLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lock-log/:id/delete',
        component: LockLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.lockLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
