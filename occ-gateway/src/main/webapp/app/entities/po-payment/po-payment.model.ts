import { BaseEntity } from './../../shared';

export class PoPayment implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public paymentSuccessful?: boolean,
        public failedReason?: string,
        public timePaid?: any,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public purchaseOrderId?: number,
    ) {
        this.paymentSuccessful = false;
    }
}
