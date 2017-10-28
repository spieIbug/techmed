import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RegimeSecuriteSociale } from './regime-securite-sociale.model';
import { RegimeSecuriteSocialePopupService } from './regime-securite-sociale-popup.service';
import { RegimeSecuriteSocialeService } from './regime-securite-sociale.service';

@Component({
    selector: 'jhi-regime-securite-sociale-delete-dialog',
    templateUrl: './regime-securite-sociale-delete-dialog.component.html'
})
export class RegimeSecuriteSocialeDeleteDialogComponent {

    regimeSecuriteSociale: RegimeSecuriteSociale;

    constructor(
        private regimeSecuriteSocialeService: RegimeSecuriteSocialeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regimeSecuriteSocialeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'regimeSecuriteSocialeListModification',
                content: 'Deleted an regimeSecuriteSociale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-regime-securite-sociale-delete-popup',
    template: ''
})
export class RegimeSecuriteSocialeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regimeSecuriteSocialePopupService: RegimeSecuriteSocialePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.regimeSecuriteSocialePopupService
                .open(RegimeSecuriteSocialeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
