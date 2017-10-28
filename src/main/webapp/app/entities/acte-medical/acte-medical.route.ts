import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActeMedicalComponent } from './acte-medical.component';
import { ActeMedicalDetailComponent } from './acte-medical-detail.component';
import { ActeMedicalPopupComponent } from './acte-medical-dialog.component';
import { ActeMedicalDeletePopupComponent } from './acte-medical-delete-dialog.component';

@Injectable()
export class ActeMedicalResolvePagingParams implements Resolve<any> {

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

export const acteMedicalRoute: Routes = [
    {
        path: 'acte-medical',
        component: ActeMedicalComponent,
        resolve: {
            'pagingParams': ActeMedicalResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.acteMedical.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'acte-medical/:id',
        component: ActeMedicalDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.acteMedical.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const acteMedicalPopupRoute: Routes = [
    {
        path: 'acte-medical-new',
        component: ActeMedicalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.acteMedical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acte-medical/:id/edit',
        component: ActeMedicalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.acteMedical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acte-medical/:id/delete',
        component: ActeMedicalDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.acteMedical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
