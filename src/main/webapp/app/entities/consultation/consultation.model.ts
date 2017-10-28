import { BaseEntity } from './../../shared';

export class Consultation implements BaseEntity {
    constructor(
        public id?: number,
        public dateActe?: any,
        public montantTTC?: number,
        public lock?: any,
        public patientId?: number,
        public medecinId?: number,
        public regimeSecuriteSocialeId?: number,
        public paiements?: BaseEntity[],
        public actesMedicalLists?: BaseEntity[],
    ) {
    }
}
