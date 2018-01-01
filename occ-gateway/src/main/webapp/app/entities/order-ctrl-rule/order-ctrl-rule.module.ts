import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OccGatewaySharedModule } from '../../shared';
import {
    OrderCtrlRuleService,
    OrderCtrlRulePopupService,
    OrderCtrlRuleComponent,
    OrderCtrlRuleDetailComponent,
    OrderCtrlRuleDialogComponent,
    OrderCtrlRulePopupComponent,
    OrderCtrlRuleDeletePopupComponent,
    OrderCtrlRuleDeleteDialogComponent,
    orderCtrlRuleRoute,
    orderCtrlRulePopupRoute,
    OrderCtrlRuleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderCtrlRuleRoute,
    ...orderCtrlRulePopupRoute,
];

@NgModule({
    imports: [
        OccGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrderCtrlRuleComponent,
        OrderCtrlRuleDetailComponent,
        OrderCtrlRuleDialogComponent,
        OrderCtrlRuleDeleteDialogComponent,
        OrderCtrlRulePopupComponent,
        OrderCtrlRuleDeletePopupComponent,
    ],
    entryComponents: [
        OrderCtrlRuleComponent,
        OrderCtrlRuleDialogComponent,
        OrderCtrlRulePopupComponent,
        OrderCtrlRuleDeleteDialogComponent,
        OrderCtrlRuleDeletePopupComponent,
    ],
    providers: [
        OrderCtrlRuleService,
        OrderCtrlRulePopupService,
        OrderCtrlRuleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayOrderCtrlRuleModule {}
