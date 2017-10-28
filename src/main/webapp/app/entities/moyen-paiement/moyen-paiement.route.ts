import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MoyenPaiementComponent } from './moyen-paiement.component';
import { MoyenPaiementDetailComponent } from './moyen-paiement-detail.component';
import { MoyenPaiementPopupComponent } from './moyen-paiement-dialog.component';
import { MoyenPaiementDeletePopupComponent } from './moyen-paiement-delete-dialog.component';

export const moyenPaiementRoute: Routes = [
    {
        path: 'moyen-paiement',
        component: MoyenPaiementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.moyenPaiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'moyen-paiement/:id',
        component: MoyenPaiementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.moyenPaiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moyenPaiementPopupRoute: Routes = [
    {
        path: 'moyen-paiement-new',
        component: MoyenPaiementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.moyenPaiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'moyen-paiement/:id/edit',
        component: MoyenPaiementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.moyenPaiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'moyen-paiement/:id/delete',
        component: MoyenPaiementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.moyenPaiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
