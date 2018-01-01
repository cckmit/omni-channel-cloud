import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    CustomerAccountService,
    CustomerAccountPopupService,
    CustomerAccountComponent,
    CustomerAccountDetailComponent,
    CustomerAccountDialogComponent,
    CustomerAccountPopupComponent,
    CustomerAccountDeletePopupComponent,
    CustomerAccountDeleteDialogComponent,
    customerAccountRoute,
    customerAccountPopupRoute,
    CustomerAccountResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerAccountRoute,
    ...customerAccountPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CustomerAccountComponent,
        CustomerAccountDetailComponent,
        CustomerAccountDialogComponent,
        CustomerAccountDeleteDialogComponent,
        CustomerAccountPopupComponent,
        CustomerAccountDeletePopupComponent,
    ],
    entryComponents: [
        CustomerAccountComponent,
        CustomerAccountDialogComponent,
        CustomerAccountPopupComponent,
        CustomerAccountDeleteDialogComponent,
        CustomerAccountDeletePopupComponent,
    ],
    providers: [
        CustomerAccountService,
        CustomerAccountPopupService,
        CustomerAccountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayCustomerAccountModule {}
