import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    SoStateService,
    SoStatePopupService,
    SoStateComponent,
    SoStateDetailComponent,
    SoStateDialogComponent,
    SoStatePopupComponent,
    SoStateDeletePopupComponent,
    SoStateDeleteDialogComponent,
    soStateRoute,
    soStatePopupRoute,
    SoStateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...soStateRoute,
    ...soStatePopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SoStateComponent,
        SoStateDetailComponent,
        SoStateDialogComponent,
        SoStateDeleteDialogComponent,
        SoStatePopupComponent,
        SoStateDeletePopupComponent,
    ],
    entryComponents: [
        SoStateComponent,
        SoStateDialogComponent,
        SoStatePopupComponent,
        SoStateDeleteDialogComponent,
        SoStateDeletePopupComponent,
    ],
    providers: [
        SoStateService,
        SoStatePopupService,
        SoStateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewaySoStateModule {}
