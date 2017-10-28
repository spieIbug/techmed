import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tarif } from './tarif.model';
import { TarifPopupService } from './tarif-popup.service';
import { TarifService } from './tarif.service';
import { ActeMedical, ActeMedicalService } from '../acte-medical';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tarif-dialog',
    templateUrl: './tarif-dialog.component.html'
})
export class TarifDialogComponent implements OnInit {

    tarif: Tarif;
    isSaving: boolean;

    actemedicals: ActeMedical[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tarifService: TarifService,
        private acteMedicalService: ActeMedicalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.acteMedicalService.query()
            .subscribe((res: ResponseWrapper) => { this.actemedicals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tarif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tarifService.update(this.tarif));
        } else {
            this.subscribeToSaveResponse(
                this.tarifService.create(this.tarif));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tarif>) {
        result.subscribe((res: Tarif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tarif) {
        this.eventManager.broadcast({ name: 'tarifListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackActeMedicalById(index: number, item: ActeMedical) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tarif-popup',
    template: ''
})
export class TarifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tarifPopupService: TarifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tarifPopupService
                    .open(TarifDialogComponent as Component, params['id']);
            } else {
                this.tarifPopupService
                    .open(TarifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
