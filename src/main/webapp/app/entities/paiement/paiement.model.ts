import { BaseEntity } from './../../shared';

export class Paiement implements BaseEntity {
    constructor(
        public id?: number,
        public dateTransation?: any,
        public montantTTC?: number,
        public consultationId?: number,
        public moyenId?: number,
    ) {
    }
}
