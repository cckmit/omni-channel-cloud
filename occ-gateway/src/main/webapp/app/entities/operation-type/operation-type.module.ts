import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    OperationTypeService,
    OperationTypePopupService,
    OperationTypeComponent,
    OperationTypeDetailComponent,
    OperationTypeDialogComponent,
    OperationTypePopupComponent,
    OperationTypeDeletePopupComponent,
    OperationTypeDeleteDialogComponent,
    operationTypeRoute,
    operationTypePopupRoute,
    OperationTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...operationTypeRoute,
    ...operationTypePopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OperationTypeComponent,
        OperationTypeDetailComponent,
        OperationTypeDialogComponent,
        OperationTypeDeleteDialogComponent,
        OperationTypePopupComponent,
        OperationTypeDeletePopupComponent,
    ],
    entryComponents: [
        OperationTypeComponent,
        OperationTypeDialogComponent,
        OperationTypePopupComponent,
        OperationTypeDeleteDialogComponent,
        OperationTypeDeletePopupComponent,
    ],
    providers: [
        OperationTypeService,
        OperationTypePopupService,
        OperationTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayOperationTypeModule {}
