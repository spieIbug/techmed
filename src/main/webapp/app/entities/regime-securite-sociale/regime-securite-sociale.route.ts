import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RegimeSecuriteSocialeComponent } from './regime-securite-sociale.component';
import { RegimeSecuriteSocialeDetailComponent } from './regime-securite-sociale-detail.component';
import { RegimeSecuriteSocialePopupComponent } from './regime-securite-sociale-dialog.component';
import { RegimeSecuriteSocialeDeletePopupComponent } from './regime-securite-sociale-delete-dialog.component';

@Injectable()
export class RegimeSecuriteSocialeResolvePagingParams implements Resolve<any> {

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

export const regimeSecuriteSocialeRoute: Routes = [
    {
        path: 'regime-securite-sociale',
        component: RegimeSecuriteSocialeComponent,
        resolve: {
            'pagingParams': RegimeSecuriteSocialeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.regimeSecuriteSociale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'regime-securite-sociale/:id',
        component: RegimeSecuriteSocialeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.regimeSecuriteSociale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regimeSecuriteSocialePopupRoute: Routes = [
    {
        path: 'regime-securite-sociale-new',
        component: RegimeSecuriteSocialePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.regimeSecuriteSociale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'regime-securite-sociale/:id/edit',
        component: RegimeSecuriteSocialePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.regimeSecuriteSociale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'regime-securite-sociale/:id/delete',
        component: RegimeSecuriteSocialeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.regimeSecuriteSociale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
