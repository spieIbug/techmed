import { BaseEntity } from './../../shared';

export class MoyenPaiement implements BaseEntity {
    constructor(
        public id?: number,
        public mode?: string,
    ) {
    }
}
