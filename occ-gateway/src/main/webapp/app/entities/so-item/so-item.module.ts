import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    SoItemService,
    SoItemPopupService,
    SoItemComponent,
    SoItemDetailComponent,
    SoItemDialogComponent,
    SoItemPopupComponent,
    SoItemDeletePopupComponent,
    SoItemDeleteDialogComponent,
    soItemRoute,
    soItemPopupRoute,
    SoItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...soItemRoute,
    ...soItemPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SoItemComponent,
        SoItemDetailComponent,
        SoItemDialogComponent,
        SoItemDeleteDialogComponent,
        SoItemPopupComponent,
        SoItemDeletePopupComponent,
    ],
    entryComponents: [
        SoItemComponent,
        SoItemDialogComponent,
        SoItemPopupComponent,
        SoItemDeleteDialogComponent,
        SoItemDeletePopupComponent,
    ],
    providers: [
        SoItemService,
        SoItemPopupService,
        SoItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewaySoItemModule {}
