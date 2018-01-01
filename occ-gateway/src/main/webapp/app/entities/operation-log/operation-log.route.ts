import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OperationLogComponent } from './operation-log.component';
import { OperationLogDetailComponent } from './operation-log-detail.component';
import { OperationLogPopupComponent } from './operation-log-dialog.component';
import { OperationLogDeletePopupComponent } from './operation-log-delete-dialog.component';

@Injectable()
export class OperationLogResolvePagingParams implements Resolve<any> {

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

export const operationLogRoute: Routes = [
    {
        path: 'operation-log',
        component: OperationLogComponent,
        resolve: {
            'pagingParams': OperationLogResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.operationLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operation-log/:id',
        component: OperationLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.operationLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operationLogPopupRoute: Routes = [
    {
        path: 'operation-log-new',
        component: OperationLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.operationLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operation-log/:id/edit',
        component: OperationLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.operationLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operation-log/:id/delete',
        component: OperationLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'occGatewayApp.operationLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
