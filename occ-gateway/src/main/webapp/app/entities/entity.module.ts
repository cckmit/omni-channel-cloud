import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OccGatewayCustomerModule } from './customer/customer.module';
import { OccGatewayCustomerAccountModule } from './customer-account/customer-account.module';
import { OccGatewayProductCategoryModule } from './product-category/product-category.module';
import { OccGatewayProductModule } from './product/product.module';
import { OccGatewayInventoryModule } from './inventory/inventory.module';
import { OccGatewayLockLogModule } from './lock-log/lock-log.module';
import { OccGatewayOperationTypeModule } from './operation-type/operation-type.module';
import { OccGatewayOperationLogModule } from './operation-log/operation-log.module';
import { OccGatewayPoTypeModule } from './po-type/po-type.module';
import { OccGatewaySoTypeModule } from './so-type/so-type.module';
import { OccGatewayPoStateModule } from './po-state/po-state.module';
import { OccGatewaySoStateModule } from './so-state/so-state.module';
import { OccGatewayOrderCtrlRuleModule } from './order-ctrl-rule/order-ctrl-rule.module';
import { OccGatewayPurchaseOrderModule } from './purchase-order/purchase-order.module';
import { OccGatewayPoItemModule } from './po-item/po-item.module';
import { OccGatewaySaleOrderModule } from './sale-order/sale-order.module';
import { OccGatewaySoItemModule } from './so-item/so-item.module';
import { OccGatewayPoPaymentModule } from './po-payment/po-payment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        OccGatewayCustomerModule,
        OccGatewayCustomerAccountModule,
        OccGatewayProductCategoryModule,
        OccGatewayProductModule,
        OccGatewayInventoryModule,
        OccGatewayLockLogModule,
        OccGatewayOperationTypeModule,
        OccGatewayOperationLogModule,
        OccGatewayPoTypeModule,
        OccGatewaySoTypeModule,
        OccGatewayPoStateModule,
        OccGatewaySoStateModule,
        OccGatewayOrderCtrlRuleModule,
        OccGatewayPurchaseOrderModule,
        OccGatewayPoItemModule,
        OccGatewaySaleOrderModule,
        OccGatewaySoItemModule,
        OccGatewayPoPaymentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OccGatewayEntityModule {}
