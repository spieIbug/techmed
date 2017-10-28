import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tarif } from './tarif.model';
import { TarifPopupService } from './tarif-popup.service';
import { TarifService } from './tarif.service';

@Component({
    selector: 'jhi-tarif-delete-dialog',
    templateUrl: './tarif-delete-dialog.component.html'
})
export class TarifDeleteDialogComponent {

    tarif: Tarif;

    constructor(
        private tarifService: TarifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tarifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tarifListModification',
                content: 'Deleted an tarif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tarif-delete-popup',
    template: ''
})
export class TarifDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tarifPopupService: TarifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tarifPopupService
                .open(TarifDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
