import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    InventoryService,
    InventoryPopupService,
    InventoryComponent,
    InventoryDetailComponent,
    InventoryDialogComponent,
    InventoryPopupComponent,
    InventoryDeletePopupComponent,
    InventoryDeleteDialogComponent,
    inventoryRoute,
    inventoryPopupRoute,
    InventoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...inventoryRoute,
    ...inventoryPopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InventoryComponent,
        InventoryDetailComponent,
        InventoryDialogComponent,
        InventoryDeleteDialogComponent,
        InventoryPopupComponent,
        InventoryDeletePopupComponent,
    ],
    entryComponents: [
        InventoryComponent,
        InventoryDialogComponent,
        InventoryPopupComponent,
        InventoryDeleteDialogComponent,
        InventoryDeletePopupComponent,
    ],
    providers: [
        InventoryService,
        InventoryPopupService,
        InventoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayInventoryModule {}
