import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CodeCCAMComponent } from './code-ccam.component';
import { CodeCCAMDetailComponent } from './code-ccam-detail.component';
import { CodeCCAMPopupComponent } from './code-ccam-dialog.component';
import { CodeCCAMDeletePopupComponent } from './code-ccam-delete-dialog.component';

@Injectable()
export class CodeCCAMResolvePagingParams implements Resolve<any> {

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

export const codeCCAMRoute: Routes = [
    {
        path: 'code-ccam',
        component: CodeCCAMComponent,
        resolve: {
            'pagingParams': CodeCCAMResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.codeCCAM.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'code-ccam/:id',
        component: CodeCCAMDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.codeCCAM.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const codeCCAMPopupRoute: Routes = [
    {
        path: 'code-ccam-new',
        component: CodeCCAMPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.codeCCAM.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'code-ccam/:id/edit',
        component: CodeCCAMPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.codeCCAM.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'code-ccam/:id/delete',
        component: CodeCCAMDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'techmedApp.codeCCAM.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
