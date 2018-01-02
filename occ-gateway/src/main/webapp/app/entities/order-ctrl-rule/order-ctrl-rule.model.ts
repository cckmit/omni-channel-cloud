import { BaseEntity } from './../../shared';

export class OrderCtrlRule implements BaseEntity {
    constructor(
        public id?: string,
        public autoPoToSo?: boolean,
        public isEnabled?: boolean,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public poTypeId?: string,
        public soTypeId?: string,
    ) {
        this.autoPoToSo = false;
        this.isEnabled = false;
    }
}
