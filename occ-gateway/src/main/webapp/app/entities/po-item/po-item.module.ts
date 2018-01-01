import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    PoItemService,
    PoItemPopupService,
    PoItemComponent,
    PoItemDetailComponent,
    PoItemDialogComponent,
    PoItemPopupComponent,
    PoItemDeletePopupComponent,
    PoItemDeleteDialogComponent,
    poItemRoute,
    poItemPopupRoute,
    PoItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...poItemRoute,
    ...poItemPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PoItemComponent,
        PoItemDetailComponent,
        PoItemDialogComponent,
        PoItemDeleteDialogComponent,
        PoItemPopupComponent,
        PoItemDeletePopupComponent,
    ],
    entryComponents: [
        PoItemComponent,
        PoItemDialogComponent,
        PoItemPopupComponent,
        PoItemDeleteDialogComponent,
        PoItemDeletePopupComponent,
    ],
    providers: [
        PoItemService,
        PoItemPopupService,
        PoItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayPoItemModule {}
