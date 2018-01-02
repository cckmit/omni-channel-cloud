import { BaseEntity } from './../../shared';

export class PoItem implements BaseEntity {
    constructor(
        public id?: string,
        public productCategoryId?: string,
        public productCategoryCode?: string,
        public productCategoryName?: string,
        public productId?: string,
        public productCode?: string,
        public productName?: string,
        public price?: number,
        public quantity?: number,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public poItemStateId?: string,
        public purchaseOrderId?: string,
    ) {
    }
}
