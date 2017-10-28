import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import { TechmedAdminModule } from '../../admin/admin.module';
import {
    ConsultationService,
    ConsultationPopupService,
    ConsultationComponent,
    ConsultationDetailComponent,
    ConsultationDialogComponent,
    ConsultationPopupComponent,
    ConsultationDeletePopupComponent,
    ConsultationDeleteDialogComponent,
    consultationRoute,
    consultationPopupRoute,
    ConsultationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...consultationRoute,
    ...consultationPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        TechmedAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConsultationComponent,
        ConsultationDetailComponent,
        ConsultationDialogComponent,
        ConsultationDeleteDialogComponent,
        ConsultationPopupComponent,
        ConsultationDeletePopupComponent,
    ],
    entryComponents: [
        ConsultationComponent,
        ConsultationDialogComponent,
        ConsultationPopupComponent,
        ConsultationDeleteDialogComponent,
        ConsultationDeletePopupComponent,
    ],
    providers: [
        ConsultationService,
        ConsultationPopupService,
        ConsultationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedConsultationModule {}
