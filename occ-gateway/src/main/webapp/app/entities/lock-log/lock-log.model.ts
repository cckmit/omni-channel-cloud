import { BaseEntity } from './../../shared';

export class LockLog implements BaseEntity {
    constructor(
        public id?: number,
        public lockedQuantity?: number,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public inventoryId?: number,
    ) {
    }
}
