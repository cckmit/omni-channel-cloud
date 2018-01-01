import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    SoTypeService,
    SoTypePopupService,
    SoTypeComponent,
    SoTypeDetailComponent,
    SoTypeDialogComponent,
    SoTypePopupComponent,
    SoTypeDeletePopupComponent,
    SoTypeDeleteDialogComponent,
    soTypeRoute,
    soTypePopupRoute,
    SoTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...soTypeRoute,
    ...soTypePopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SoTypeComponent,
        SoTypeDetailComponent,
        SoTypeDialogComponent,
        SoTypeDeleteDialogComponent,
        SoTypePopupComponent,
        SoTypeDeletePopupComponent,
    ],
    entryComponents: [
        SoTypeComponent,
        SoTypeDialogComponent,
        SoTypePopupComponent,
        SoTypeDeleteDialogComponent,
        SoTypeDeletePopupComponent,
    ],
    providers: [
        SoTypeService,
        SoTypePopupService,
        SoTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewaySoTypeModule {}
