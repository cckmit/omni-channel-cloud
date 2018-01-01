import { BaseEntity } from './../../shared';

export class SoItem implements BaseEntity {
    constructor(
        public id?: number,
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
        public soItemStateId?: number,
        public saleOrderId?: number,
    ) {
    }
}
