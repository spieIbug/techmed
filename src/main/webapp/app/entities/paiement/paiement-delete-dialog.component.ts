import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Paiement } from './paiement.model';
import { PaiementPopupService } from './paiement-popup.service';
import { PaiementService } from './paiement.service';

@Component({
    selector: 'jhi-paiement-delete-dialog',
    templateUrl: './paiement-delete-dialog.component.html'
})
export class PaiementDeleteDialogComponent {

    paiement: Paiement;

    constructor(
        private paiementService: PaiementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paiementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paiementListModification',
                content: 'Deleted an paiement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-paiement-delete-popup',
    template: ''
})
export class PaiementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paiementPopupService: PaiementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.paiementPopupService
                .open(PaiementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
