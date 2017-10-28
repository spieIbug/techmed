import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Paiement } from './paiement.model';
import { PaiementPopupService } from './paiement-popup.service';
import { PaiementService } from './paiement.service';
import { Consultation, ConsultationService } from '../consultation';
import { MoyenPaiement, MoyenPaiementService } from '../moyen-paiement';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-paiement-dialog',
    templateUrl: './paiement-dialog.component.html'
})
export class PaiementDialogComponent implements OnInit {

    paiement: Paiement;
    isSaving: boolean;

    consultations: Consultation[];

    moyens: MoyenPaiement[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private paiementService: PaiementService,
        private consultationService: ConsultationService,
        private moyenPaiementService: MoyenPaiementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.consultationService.query()
            .subscribe((res: ResponseWrapper) => { this.consultations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.moyenPaiementService
            .query({filter: 'paiement-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.paiement.moyenId) {
                    this.moyens = res.json;
                } else {
                    this.moyenPaiementService
                        .find(this.paiement.moyenId)
                        .subscribe((subRes: MoyenPaiement) => {
                            this.moyens = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paiement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paiementService.update(this.paiement));
        } else {
            this.subscribeToSaveResponse(
                this.paiementService.create(this.paiement));
        }
    }

    private subscribeToSaveResponse(result: Observable<Paiement>) {
        result.subscribe((res: Paiement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Paiement) {
        this.eventManager.broadcast({ name: 'paiementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConsultationById(index: number, item: Consultation) {
        return item.id;
    }

    trackMoyenPaiementById(index: number, item: MoyenPaiement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-paiement-popup',
    template: ''
})
export class PaiementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paiementPopupService: PaiementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paiementPopupService
                    .open(PaiementDialogComponent as Component, params['id']);
            } else {
                this.paiementPopupService
                    .open(PaiementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
