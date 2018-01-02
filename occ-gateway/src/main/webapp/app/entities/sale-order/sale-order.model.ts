import { BaseEntity } from './../../shared';

export class SaleOrder implements BaseEntity {
    constructor(
        public id?: string,
        public code?: string,
        public orderDate?: any,
        public totalAmount?: number,
        public customerId?: string,
        public customerCode?: string,
        public customerName?: string,
        public accountId?: string,
        public accountCode?: string,
        public accountName?: string,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public soItems?: BaseEntity[],
        public soTypeId?: string,
        public soStateId?: string,
    ) {
    }
}
