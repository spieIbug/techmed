import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Consultation } from './consultation.model';
import { ConsultationPopupService } from './consultation-popup.service';
import { ConsultationService } from './consultation.service';
import { Patient, PatientService } from '../patient';
import { User, UserService } from '../../shared';
import { RegimeSecuriteSociale, RegimeSecuriteSocialeService } from '../regime-securite-sociale';
import { ActeMedical, ActeMedicalService } from '../acte-medical';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consultation-dialog',
    templateUrl: './consultation-dialog.component.html'
})
export class ConsultationDialogComponent implements OnInit {

    consultation: Consultation;
    isSaving: boolean;

    patients: Patient[];

    users: User[];

    regimesecuritesociales: RegimeSecuriteSociale[];

    actemedicals: ActeMedical[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private consultationService: ConsultationService,
        private patientService: PatientService,
        private userService: UserService,
        private regimeSecuriteSocialeService: RegimeSecuriteSocialeService,
        private acteMedicalService: ActeMedicalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.patientService
            .query({filter: 'consultation-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.consultation.patientId) {
                    this.patients = res.json;
                } else {
                    this.patientService
                        .find(this.consultation.patientId)
                        .subscribe((subRes: Patient) => {
                            this.patients = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.regimeSecuriteSocialeService
            .query({filter: 'consultation-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.consultation.regimeSecuriteSocialeId) {
                    this.regimesecuritesociales = res.json;
                } else {
                    this.regimeSecuriteSocialeService
                        .find(this.consultation.regimeSecuriteSocialeId)
                        .subscribe((subRes: RegimeSecuriteSociale) => {
                            this.regimesecuritesociales = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.acteMedicalService.query()
            .subscribe((res: ResponseWrapper) => { this.actemedicals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.consultation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultationService.update(this.consultation));
        } else {
            this.subscribeToSaveResponse(
                this.consultationService.create(this.consultation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Consultation>) {
        result.subscribe((res: Consultation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Consultation) {
        this.eventManager.broadcast({ name: 'consultationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPatientById(index: number, item: Patient) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackRegimeSecuriteSocialeById(index: number, item: RegimeSecuriteSociale) {
        return item.id;
    }

    trackActeMedicalById(index: number, item: ActeMedical) {
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
    selector: 'jhi-consultation-popup',
    template: ''
})
export class ConsultationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultationPopupService: ConsultationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultationPopupService
                    .open(ConsultationDialogComponent as Component, params['id']);
            } else {
                this.consultationPopupService
                    .open(ConsultationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
