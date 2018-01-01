import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    SaleOrderService,
    SaleOrderPopupService,
    SaleOrderComponent,
    SaleOrderDetailComponent,
    SaleOrderDialogComponent,
    SaleOrderPopupComponent,
    SaleOrderDeletePopupComponent,
    SaleOrderDeleteDialogComponent,
    saleOrderRoute,
    saleOrderPopupRoute,
    SaleOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...saleOrderRoute,
    ...saleOrderPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SaleOrderComponent,
        SaleOrderDetailComponent,
        SaleOrderDialogComponent,
        SaleOrderDeleteDialogComponent,
        SaleOrderPopupComponent,
        SaleOrderDeletePopupComponent,
    ],
    entryComponents: [
        SaleOrderComponent,
        SaleOrderDialogComponent,
        SaleOrderPopupComponent,
        SaleOrderDeleteDialogComponent,
        SaleOrderDeletePopupComponent,
    ],
    providers: [
        SaleOrderService,
        SaleOrderPopupService,
        SaleOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewaySaleOrderModule {}
