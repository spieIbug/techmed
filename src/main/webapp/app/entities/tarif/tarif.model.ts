import { BaseEntity } from './../../shared';

export class Tarif implements BaseEntity {
    constructor(
        public id?: number,
        public prixHT?: number,
        public tva?: number,
        public prixTTC?: number,
        public actif?: boolean,
        public dateDebut?: any,
        public dateFin?: any,
        public acteMedicalId?: number,
    ) {
        this.actif = false;
    }
}
