import { BaseEntity } from './../../shared';

export class CodeCCAM implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public acteMedicalLists?: BaseEntity[],
    ) {
    }
}
