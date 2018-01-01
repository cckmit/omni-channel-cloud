import { BaseEntity } from './../../shared';

export class OrderCtrlRule implements BaseEntity {
    constructor(
        public id?: number,
        public autoPoToSo?: boolean,
        public isEnabled?: boolean,
        public version?: number,
        public dr?: number,
        public ts?: any,
        public creator?: string,
        public timeCreated?: any,
        public modifier?: string,
        public timeModified?: any,
        public poTypeId?: number,
        public soTypeId?: number,
    ) {
        this.autoPoToSo = false;
        this.isEnabled = false;
    }
}
