import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    PoStateService,
    PoStatePopupService,
    PoStateComponent,
    PoStateDetailComponent,
    PoStateDialogComponent,
    PoStatePopupComponent,
    PoStateDeletePopupComponent,
    PoStateDeleteDialogComponent,
    poStateRoute,
    poStatePopupRoute,
    PoStateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...poStateRoute,
    ...poStatePopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PoStateComponent,
        PoStateDetailComponent,
        PoStateDialogComponent,
        PoStateDeleteDialogComponent,
        PoStatePopupComponent,
        PoStateDeletePopupComponent,
    ],
    entryComponents: [
        PoStateComponent,
        PoStateDialogComponent,
        PoStatePopupComponent,
        PoStateDeleteDialogComponent,
        PoStateDeletePopupComponent,
    ],
    providers: [
        PoStateService,
        PoStatePopupService,
        PoStateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayPoStateModule {}
