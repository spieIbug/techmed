import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    TarifService,
    TarifPopupService,
    TarifComponent,
    TarifDetailComponent,
    TarifDialogComponent,
    TarifPopupComponent,
    TarifDeletePopupComponent,
    TarifDeleteDialogComponent,
    tarifRoute,
    tarifPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tarifRoute,
    ...tarifPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TarifComponent,
        TarifDetailComponent,
        TarifDialogComponent,
        TarifDeleteDialogComponent,
        TarifPopupComponent,
        TarifDeletePopupComponent,
    ],
    entryComponents: [
        TarifComponent,
        TarifDialogComponent,
        TarifPopupComponent,
        TarifDeleteDialogComponent,
        TarifDeletePopupComponent,
    ],
    providers: [
        TarifService,
        TarifPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedTarifModule {}
