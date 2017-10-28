import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ActeMedical } from './acte-medical.model';
import { ActeMedicalPopupService } from './acte-medical-popup.service';
import { ActeMedicalService } from './acte-medical.service';

@Component({
    selector: 'jhi-acte-medical-delete-dialog',
    templateUrl: './acte-medical-delete-dialog.component.html'
})
export class ActeMedicalDeleteDialogComponent {

    acteMedical: ActeMedical;

    constructor(
        private acteMedicalService: ActeMedicalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.acteMedicalService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'acteMedicalListModification',
                content: 'Deleted an acteMedical'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-acte-medical-delete-popup',
    template: ''
})
export class ActeMedicalDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private acteMedicalPopupService: ActeMedicalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.acteMedicalPopupService
                .open(ActeMedicalDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
