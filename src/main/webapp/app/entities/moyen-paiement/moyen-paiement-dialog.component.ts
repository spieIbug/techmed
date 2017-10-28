import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MoyenPaiement } from './moyen-paiement.model';
import { MoyenPaiementPopupService } from './moyen-paiement-popup.service';
import { MoyenPaiementService } from './moyen-paiement.service';

@Component({
    selector: 'jhi-moyen-paiement-dialog',
    templateUrl: './moyen-paiement-dialog.component.html'
})
export class MoyenPaiementDialogComponent implements OnInit {

    moyenPaiement: MoyenPaiement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private moyenPaiementService: MoyenPaiementService,
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
        if (this.moyenPaiement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.moyenPaiementService.update(this.moyenPaiement));
        } else {
            this.subscribeToSaveResponse(
                this.moyenPaiementService.create(this.moyenPaiement));
        }
    }

    private subscribeToSaveResponse(result: Observable<MoyenPaiement>) {
        result.subscribe((res: MoyenPaiement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MoyenPaiement) {
        this.eventManager.broadcast({ name: 'moyenPaiementListModification', content: 'OK'});
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
    selector: 'jhi-moyen-paiement-popup',
    template: ''
})
export class MoyenPaiementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moyenPaiementPopupService: MoyenPaiementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.moyenPaiementPopupService
                    .open(MoyenPaiementDialogComponent as Component, params['id']);
            } else {
                this.moyenPaiementPopupService
                    .open(MoyenPaiementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
