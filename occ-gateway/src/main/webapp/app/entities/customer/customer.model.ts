import { BaseEntity } from './../../shared';

export class Customer implements BaseEntity {
    constructor(
        public id?: string,
        public code?: string,
        public name?: string,
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
