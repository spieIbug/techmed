import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    ActeMedicalService,
    ActeMedicalPopupService,
    ActeMedicalComponent,
    ActeMedicalDetailComponent,
    ActeMedicalDialogComponent,
    ActeMedicalPopupComponent,
    ActeMedicalDeletePopupComponent,
    ActeMedicalDeleteDialogComponent,
    acteMedicalRoute,
    acteMedicalPopupRoute,
    ActeMedicalResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...acteMedicalRoute,
    ...acteMedicalPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActeMedicalComponent,
        ActeMedicalDetailComponent,
        ActeMedicalDialogComponent,
        ActeMedicalDeleteDialogComponent,
        ActeMedicalPopupComponent,
        ActeMedicalDeletePopupComponent,
    ],
    entryComponents: [
        ActeMedicalComponent,
        ActeMedicalDialogComponent,
        ActeMedicalPopupComponent,
        ActeMedicalDeleteDialogComponent,
        ActeMedicalDeletePopupComponent,
    ],
    providers: [
        ActeMedicalService,
        ActeMedicalPopupService,
        ActeMedicalResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedActeMedicalModule {}
