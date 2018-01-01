import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    LockLogService,
    LockLogPopupService,
    LockLogComponent,
    LockLogDetailComponent,
    LockLogDialogComponent,
    LockLogPopupComponent,
    LockLogDeletePopupComponent,
    LockLogDeleteDialogComponent,
    lockLogRoute,
    lockLogPopupRoute,
    LockLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lockLogRoute,
    ...lockLogPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LockLogComponent,
        LockLogDetailComponent,
        LockLogDialogComponent,
        LockLogDeleteDialogComponent,
        LockLogPopupComponent,
        LockLogDeletePopupComponent,
    ],
    entryComponents: [
        LockLogComponent,
        LockLogDialogComponent,
        LockLogPopupComponent,
        LockLogDeleteDialogComponent,
        LockLogDeletePopupComponent,
    ],
    providers: [
        LockLogService,
        LockLogPopupService,
        LockLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayLockLogModule {}
