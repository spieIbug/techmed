import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConsultationComponent } from './consultation.component';
import { ConsultationDetailComponent } from './consultation-detail.component';
import { ConsultationPopupComponent } from './consultation-dialog.component';
import { ConsultationDeletePopupComponent } from './consultation-delete-dialog.component';

@Injectable()
export class ConsultationResolvePagingParams implements Resolve<any> {

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

export const consultationRoute: Routes = [
    {
        path: 'consultation',
        component: ConsultationComponent,
        resolve: {
            'pagingParams': ConsultationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.consultation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consultation/:id',
        component: ConsultationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.consultation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultationPopupRoute: Routes = [
    {
        path: 'consultation-new',
        component: ConsultationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.consultation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultation/:id/edit',
        component: ConsultationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.consultation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultation/:id/delete',
        component: ConsultationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.consultation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
