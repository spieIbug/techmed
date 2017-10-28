import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Consultation } from './consultation.model';
import { ConsultationPopupService } from './consultation-popup.service';
import { ConsultationService } from './consultation.service';

@Component({
    selector: 'jhi-consultation-delete-dialog',
    templateUrl: './consultation-delete-dialog.component.html'
})
export class ConsultationDeleteDialogComponent {

    consultation: Consultation;

    constructor(
        private consultationService: ConsultationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultationListModification',
                content: 'Deleted an consultation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consultation-delete-popup',
    template: ''
})
export class ConsultationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultationPopupService: ConsultationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultationPopupService
                .open(ConsultationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
