import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    OperationLogService,
    OperationLogPopupService,
    OperationLogComponent,
    OperationLogDetailComponent,
    OperationLogDialogComponent,
    OperationLogPopupComponent,
    OperationLogDeletePopupComponent,
    OperationLogDeleteDialogComponent,
    operationLogRoute,
    operationLogPopupRoute,
    OperationLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...operationLogRoute,
    ...operationLogPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OperationLogComponent,
        OperationLogDetailComponent,
        OperationLogDialogComponent,
        OperationLogDeleteDialogComponent,
        OperationLogPopupComponent,
        OperationLogDeletePopupComponent,
    ],
    entryComponents: [
        OperationLogComponent,
        OperationLogDialogComponent,
        OperationLogPopupComponent,
        OperationLogDeleteDialogComponent,
        OperationLogDeletePopupComponent,
    ],
    providers: [
        OperationLogService,
        OperationLogPopupService,
        OperationLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayOperationLogModule {}
