import { BaseEntity } from './../../shared';

export class ActeMedical implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public tarifs?: BaseEntity[],
        public consultationLists?: BaseEntity[],
        public codeCCAMId?: number,
    ) {
    }
}
