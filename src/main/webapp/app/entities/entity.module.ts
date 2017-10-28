import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TechmedPatientModule } from './patient/patient.module';
import { TechmedActeMedicalModule } from './acte-medical/acte-medical.module';
import { TechmedCodeCCAMModule } from './code-ccam/code-ccam.module';
import { TechmedMoyenPaiementModule } from './moyen-paiement/moyen-paiement.module';
import { TechmedTarifModule } from './tarif/tarif.module';
import { TechmedRegimeSecuriteSocialeModule } from './regime-securite-sociale/regime-securite-sociale.module';
import { TechmedConsultationModule } from './consultation/consultation.module';
import { TechmedPaiementModule } from './paiement/paiement.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TechmedPatientModule,
        TechmedActeMedicalModule,
        TechmedCodeCCAMModule,
        TechmedMoyenPaiementModule,
        TechmedTarifModule,
        TechmedRegimeSecuriteSocialeModule,
        TechmedConsultationModule,
        TechmedPaiementModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedEntityModule {}
