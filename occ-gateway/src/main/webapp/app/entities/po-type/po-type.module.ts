import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    PoTypeService,
    PoTypePopupService,
    PoTypeComponent,
    PoTypeDetailComponent,
    PoTypeDialogComponent,
    PoTypePopupComponent,
    PoTypeDeletePopupComponent,
    PoTypeDeleteDialogComponent,
    poTypeRoute,
    poTypePopupRoute,
    PoTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...poTypeRoute,
    ...poTypePopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PoTypeComponent,
        PoTypeDetailComponent,
        PoTypeDialogComponent,
        PoTypeDeleteDialogComponent,
        PoTypePopupComponent,
        PoTypeDeletePopupComponent,
    ],
    entryComponents: [
        PoTypeComponent,
        PoTypeDialogComponent,
        PoTypePopupComponent,
        PoTypeDeleteDialogComponent,
        PoTypeDeletePopupComponent,
    ],
    providers: [
        PoTypeService,
        PoTypePopupService,
        PoTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayPoTypeModule {}
