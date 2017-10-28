import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CodeCCAM } from './code-ccam.model';
import { CodeCCAMPopupService } from './code-ccam-popup.service';
import { CodeCCAMService } from './code-ccam.service';

@Component({
    selector: 'jhi-code-ccam-dialog',
    templateUrl: './code-ccam-dialog.component.html'
})
export class CodeCCAMDialogComponent implements OnInit {

    codeCCAM: CodeCCAM;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private codeCCAMService: CodeCCAMService,
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
        if (this.codeCCAM.id !== undefined) {
            this.subscribeToSaveResponse(
                this.codeCCAMService.update(this.codeCCAM));
        } else {
            this.subscribeToSaveResponse(
                this.codeCCAMService.create(this.codeCCAM));
        }
    }

    private subscribeToSaveResponse(result: Observable<CodeCCAM>) {
        result.subscribe((res: CodeCCAM) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CodeCCAM) {
        this.eventManager.broadcast({ name: 'codeCCAMListModification', content: 'OK'});
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
    selector: 'jhi-code-ccam-popup',
    template: ''
})
export class CodeCCAMPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codeCCAMPopupService: CodeCCAMPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.codeCCAMPopupService
                    .open(CodeCCAMDialogComponent as Component, params['id']);
            } else {
                this.codeCCAMPopupService
                    .open(CodeCCAMDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
