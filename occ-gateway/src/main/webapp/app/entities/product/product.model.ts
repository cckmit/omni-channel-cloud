import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public desc?: string,
        public price?: number,
        public isEnabled?: boolean,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public productCategoryId?: number,
    ) {
        this.isEnabled = false;
    }
}
