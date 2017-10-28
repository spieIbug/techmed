import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CodeCCAM } from './code-ccam.model';
import { CodeCCAMPopupService } from './code-ccam-popup.service';
import { CodeCCAMService } from './code-ccam.service';

@Component({
    selector: 'jhi-code-ccam-delete-dialog',
    templateUrl: './code-ccam-delete-dialog.component.html'
})
export class CodeCCAMDeleteDialogComponent {

    codeCCAM: CodeCCAM;

    constructor(
        private codeCCAMService: CodeCCAMService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.codeCCAMService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'codeCCAMListModification',
                content: 'Deleted an codeCCAM'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-code-ccam-delete-popup',
    template: ''
})
export class CodeCCAMDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private codeCCAMPopupService: CodeCCAMPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.codeCCAMPopupService
                .open(CodeCCAMDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
