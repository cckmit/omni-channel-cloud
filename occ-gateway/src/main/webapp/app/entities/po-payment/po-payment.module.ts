import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    PoPaymentService,
    PoPaymentPopupService,
    PoPaymentComponent,
    PoPaymentDetailComponent,
    PoPaymentDialogComponent,
    PoPaymentPopupComponent,
    PoPaymentDeletePopupComponent,
    PoPaymentDeleteDialogComponent,
    poPaymentRoute,
    poPaymentPopupRoute,
    PoPaymentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...poPaymentRoute,
    ...poPaymentPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PoPaymentComponent,
        PoPaymentDetailComponent,
        PoPaymentDialogComponent,
        PoPaymentDeleteDialogComponent,
        PoPaymentPopupComponent,
        PoPaymentDeletePopupComponent,
    ],
    entryComponents: [
        PoPaymentComponent,
        PoPaymentDialogComponent,
        PoPaymentPopupComponent,
        PoPaymentDeleteDialogComponent,
        PoPaymentDeletePopupComponent,
    ],
    providers: [
        PoPaymentService,
        PoPaymentPopupService,
        PoPaymentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayPoPaymentModule {}
