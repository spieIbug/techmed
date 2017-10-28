import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    PaiementService,
    PaiementPopupService,
    PaiementComponent,
    PaiementDetailComponent,
    PaiementDialogComponent,
    PaiementPopupComponent,
    PaiementDeletePopupComponent,
    PaiementDeleteDialogComponent,
    paiementRoute,
    paiementPopupRoute,
    PaiementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...paiementRoute,
    ...paiementPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaiementComponent,
        PaiementDetailComponent,
        PaiementDialogComponent,
        PaiementDeleteDialogComponent,
        PaiementPopupComponent,
        PaiementDeletePopupComponent,
    ],
    entryComponents: [
        PaiementComponent,
        PaiementDialogComponent,
        PaiementPopupComponent,
        PaiementDeleteDialogComponent,
        PaiementDeletePopupComponent,
    ],
    providers: [
        PaiementService,
        PaiementPopupService,
        PaiementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedPaiementModule {}
