import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    PatientService,
    PatientPopupService,
    PatientComponent,
    PatientDetailComponent,
    PatientDialogComponent,
    PatientPopupComponent,
    PatientDeletePopupComponent,
    PatientDeleteDialogComponent,
    patientRoute,
    patientPopupRoute,
    PatientResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...patientRoute,
    ...patientPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PatientComponent,
        PatientDetailComponent,
        PatientDialogComponent,
        PatientDeleteDialogComponent,
        PatientPopupComponent,
        PatientDeletePopupComponent,
    ],
    entryComponents: [
        PatientComponent,
        PatientDialogComponent,
        PatientPopupComponent,
        PatientDeleteDialogComponent,
        PatientDeletePopupComponent,
    ],
    providers: [
        PatientService,
        PatientPopupService,
        PatientResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedPatientModule {}
