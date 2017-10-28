import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MoyenPaiement } from './moyen-paiement.model';
import { MoyenPaiementPopupService } from './moyen-paiement-popup.service';
import { MoyenPaiementService } from './moyen-paiement.service';

@Component({
    selector: 'jhi-moyen-paiement-delete-dialog',
    templateUrl: './moyen-paiement-delete-dialog.component.html'
})
export class MoyenPaiementDeleteDialogComponent {

    moyenPaiement: MoyenPaiement;

    constructor(
        private moyenPaiementService: MoyenPaiementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moyenPaiementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'moyenPaiementListModification',
                content: 'Deleted an moyenPaiement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-moyen-paiement-delete-popup',
    template: ''
})
export class MoyenPaiementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moyenPaiementPopupService: MoyenPaiementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.moyenPaiementPopupService
                .open(MoyenPaiementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
