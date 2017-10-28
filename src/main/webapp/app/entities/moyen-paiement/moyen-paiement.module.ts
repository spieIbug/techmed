import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    MoyenPaiementService,
    MoyenPaiementPopupService,
    MoyenPaiementComponent,
    MoyenPaiementDetailComponent,
    MoyenPaiementDialogComponent,
    MoyenPaiementPopupComponent,
    MoyenPaiementDeletePopupComponent,
    MoyenPaiementDeleteDialogComponent,
    moyenPaiementRoute,
    moyenPaiementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...moyenPaiementRoute,
    ...moyenPaiementPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MoyenPaiementComponent,
        MoyenPaiementDetailComponent,
        MoyenPaiementDialogComponent,
        MoyenPaiementDeleteDialogComponent,
        MoyenPaiementPopupComponent,
        MoyenPaiementDeletePopupComponent,
    ],
    entryComponents: [
        MoyenPaiementComponent,
        MoyenPaiementDialogComponent,
        MoyenPaiementPopupComponent,
        MoyenPaiementDeleteDialogComponent,
        MoyenPaiementDeletePopupComponent,
    ],
    providers: [
        MoyenPaiementService,
        MoyenPaiementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedMoyenPaiementModule {}
