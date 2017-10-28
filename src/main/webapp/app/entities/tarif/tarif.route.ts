import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TarifComponent } from './tarif.component';
import { TarifDetailComponent } from './tarif-detail.component';
import { TarifPopupComponent } from './tarif-dialog.component';
import { TarifDeletePopupComponent } from './tarif-delete-dialog.component';

export const tarifRoute: Routes = [
    {
        path: 'tarif',
        component: TarifComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tarif/:id',
        component: TarifDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tarifPopupRoute: Routes = [
    {
        path: 'tarif-new',
        component: TarifPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tarif/:id/edit',
        component: TarifPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tarif/:id/delete',
        component: TarifDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
