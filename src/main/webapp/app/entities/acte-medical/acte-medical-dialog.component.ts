import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ActeMedical } from './acte-medical.model';
import { ActeMedicalPopupService } from './acte-medical-popup.service';
import { ActeMedicalService } from './acte-medical.service';
import { Consultation, ConsultationService } from '../consultation';
import { CodeCCAM, CodeCCAMService } from '../code-ccam';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-acte-medical-dialog',
    templateUrl: './acte-medical-dialog.component.html'
})
export class ActeMedicalDialogComponent implements OnInit {

    acteMedical: ActeMedical;
    isSaving: boolean;

    consultations: Consultation[];

    codeccams: CodeCCAM[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private acteMedicalService: ActeMedicalService,
        private consultationService: ConsultationService,
        private codeCCAMService: CodeCCAMService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.consultationService.query()
            .subscribe((res: ResponseWrapper) => { this.consultations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.codeCCAMService.query()
            .subscribe((res: ResponseWrapper) => { this.codeccams = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.acteMedical.id !== undefined) {
            this.subscribeToSaveResponse(
                this.acteMedicalService.update(this.acteMedical));
        } else {
            this.subscribeToSaveResponse(
                this.acteMedicalService.create(this.acteMedical));
        }
    }

    private subscribeToSaveResponse(result: Observable<ActeMedical>) {
        result.subscribe((res: ActeMedical) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ActeMedical) {
        this.eventManager.broadcast({ name: 'acteMedicalListModification', content: 'OK'});
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

    trackCodeCCAMById(index: number, item: CodeCCAM) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-acte-medical-popup',
    template: ''
})
export class ActeMedicalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private acteMedicalPopupService: ActeMedicalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.acteMedicalPopupService
                    .open(ActeMedicalDialogComponent as Component, params['id']);
            } else {
                this.acteMedicalPopupService
                    .open(ActeMedicalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
