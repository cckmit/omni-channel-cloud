import { BaseEntity } from './../../shared';

export class OperationLog implements BaseEntity {
    constructor(
        public id?: number,
        public operationQuantity?: number,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public operationTypeId?: number,
        public inventoryId?: number,
    ) {
    }
}
