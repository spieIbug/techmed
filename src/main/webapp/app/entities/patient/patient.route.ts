import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PatientComponent } from './patient.component';
import { PatientDetailComponent } from './patient-detail.component';
import { PatientPopupComponent } from './patient-dialog.component';
import { PatientDeletePopupComponent } from './patient-delete-dialog.component';

@Injectable()
export class PatientResolvePagingParams implements Resolve<any> {

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

export const patientRoute: Routes = [
    {
        path: 'patient',
        component: PatientComponent,
        resolve: {
            'pagingParams': PatientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'patient/:id',
        component: PatientDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patientPopupRoute: Routes = [
    {
        path: 'patient-new',
        component: PatientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'patient/:id/edit',
        component: PatientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'patient/:id/delete',
        component: PatientDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
