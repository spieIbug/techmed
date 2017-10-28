import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PaiementComponent } from './paiement.component';
import { PaiementDetailComponent } from './paiement-detail.component';
import { PaiementPopupComponent } from './paiement-dialog.component';
import { PaiementDeletePopupComponent } from './paiement-delete-dialog.component';

@Injectable()
export class PaiementResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const paiementRoute: Routes = [
    {
        path: 'paiement',
        component: PaiementComponent,
        resolve: {
            'pagingParams': PaiementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'paiement/:id',
        component: PaiementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paiementPopupRoute: Routes = [
    {
        path: 'paiement-new',
        component: PaiementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paiement/:id/edit',
        component: PaiementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paiement/:id/delete',
        component: PaiementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
