import { BaseEntity } from './../../shared';

export class Inventory implements BaseEntity {
    constructor(
        public id?: string,
        public productId?: string,
        public productCode?: string,
        public productName?: string,
        public toSellQuantity?: number,
        public lockedQuantity?: number,
        public saledQuantity?: number,
        public isEnabled?: boolean,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
    ) {
        this.isEnabled = false;
    }
}
