import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    RegimeSecuriteSocialeService,
    RegimeSecuriteSocialePopupService,
    RegimeSecuriteSocialeComponent,
    RegimeSecuriteSocialeDetailComponent,
    RegimeSecuriteSocialeDialogComponent,
    RegimeSecuriteSocialePopupComponent,
    RegimeSecuriteSocialeDeletePopupComponent,
    RegimeSecuriteSocialeDeleteDialogComponent,
    regimeSecuriteSocialeRoute,
    regimeSecuriteSocialePopupRoute,
    RegimeSecuriteSocialeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...regimeSecuriteSocialeRoute,
    ...regimeSecuriteSocialePopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RegimeSecuriteSocialeComponent,
        RegimeSecuriteSocialeDetailComponent,
        RegimeSecuriteSocialeDialogComponent,
        RegimeSecuriteSocialeDeleteDialogComponent,
        RegimeSecuriteSocialePopupComponent,
        RegimeSecuriteSocialeDeletePopupComponent,
    ],
    entryComponents: [
        RegimeSecuriteSocialeComponent,
        RegimeSecuriteSocialeDialogComponent,
        RegimeSecuriteSocialePopupComponent,
        RegimeSecuriteSocialeDeleteDialogComponent,
        RegimeSecuriteSocialeDeletePopupComponent,
    ],
    providers: [
        RegimeSecuriteSocialeService,
        RegimeSecuriteSocialePopupService,
        RegimeSecuriteSocialeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedRegimeSecuriteSocialeModule {}
