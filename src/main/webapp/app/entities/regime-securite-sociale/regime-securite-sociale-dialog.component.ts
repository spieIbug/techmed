import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RegimeSecuriteSociale } from './regime-securite-sociale.model';
import { RegimeSecuriteSocialePopupService } from './regime-securite-sociale-popup.service';
import { RegimeSecuriteSocialeService } from './regime-securite-sociale.service';

@Component({
    selector: 'jhi-regime-securite-sociale-dialog',
    templateUrl: './regime-securite-sociale-dialog.component.html'
})
export class RegimeSecuriteSocialeDialogComponent implements OnInit {

    regimeSecuriteSociale: RegimeSecuriteSociale;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private regimeSecuriteSocialeService: RegimeSecuriteSocialeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.regimeSecuriteSociale.id !== undefined) {
            this.subscribeToSaveResponse(
                this.regimeSecuriteSocialeService.update(this.regimeSecuriteSociale));
        } else {
            this.subscribeToSaveResponse(
                this.regimeSecuriteSocialeService.create(this.regimeSecuriteSociale));
        }
    }

    private subscribeToSaveResponse(result: Observable<RegimeSecuriteSociale>) {
        result.subscribe((res: RegimeSecuriteSociale) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RegimeSecuriteSociale) {
        this.eventManager.broadcast({ name: 'regimeSecuriteSocialeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-regime-securite-sociale-popup',
    template: ''
})
export class RegimeSecuriteSocialePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regimeSecuriteSocialePopupService: RegimeSecuriteSocialePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.regimeSecuriteSocialePopupService
                    .open(RegimeSecuriteSocialeDialogComponent as Component, params['id']);
            } else {
                this.regimeSecuriteSocialePopupService
                    .open(RegimeSecuriteSocialeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
