import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    PurchaseOrderService,
    PurchaseOrderPopupService,
    PurchaseOrderComponent,
    PurchaseOrderDetailComponent,
    PurchaseOrderDialogComponent,
    PurchaseOrderPopupComponent,
    PurchaseOrderDeletePopupComponent,
    PurchaseOrderDeleteDialogComponent,
    purchaseOrderRoute,
    purchaseOrderPopupRoute,
    PurchaseOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...purchaseOrderRoute,
    ...purchaseOrderPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderDialogComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderPopupComponent,
        PurchaseOrderDeletePopupComponent,
    ],
    entryComponents: [
        PurchaseOrderComponent,
        PurchaseOrderDialogComponent,
        PurchaseOrderPopupComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
    ],
    providers: [
        PurchaseOrderService,
        PurchaseOrderPopupService,
        PurchaseOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayPurchaseOrderModule {}
