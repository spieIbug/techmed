import { BaseEntity } from './../../shared';

export class RegimeSecuriteSociale implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
    ) {
    }
}
