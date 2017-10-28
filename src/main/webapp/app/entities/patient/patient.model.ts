import { BaseEntity } from './../../shared';

export class Patient implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public dateNaissance?: any,
    ) {
    }
}
