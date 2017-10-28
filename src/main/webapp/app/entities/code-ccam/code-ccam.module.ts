import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechmedSharedModule } from '../../shared';
import {
    CodeCCAMService,
    CodeCCAMPopupService,
    CodeCCAMComponent,
    CodeCCAMDetailComponent,
    CodeCCAMDialogComponent,
    CodeCCAMPopupComponent,
    CodeCCAMDeletePopupComponent,
    CodeCCAMDeleteDialogComponent,
    codeCCAMRoute,
    codeCCAMPopupRoute,
    CodeCCAMResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...codeCCAMRoute,
    ...codeCCAMPopupRoute,
];

@NgModule({
    imports: [
        TechmedSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CodeCCAMComponent,
        CodeCCAMDetailComponent,
        CodeCCAMDialogComponent,
        CodeCCAMDeleteDialogComponent,
        CodeCCAMPopupComponent,
        CodeCCAMDeletePopupComponent,
    ],
    entryComponents: [
        CodeCCAMComponent,
        CodeCCAMDialogComponent,
        CodeCCAMPopupComponent,
        CodeCCAMDeleteDialogComponent,
        CodeCCAMDeletePopupComponent,
    ],
    providers: [
        CodeCCAMService,
        CodeCCAMPopupService,
        CodeCCAMResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TechmedCodeCCAMModule {}
