import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MoyenPaiement } from './moyen-paiement.model';
import { MoyenPaiementService } from './moyen-paiement.service';

@Component({
    selector: 'jhi-moyen-paiement-detail',
    templateUrl: './moyen-paiement-detail.component.html'
})
export class MoyenPaiementDetailComponent implements OnInit, OnDestroy {

    moyenPaiement: MoyenPaiement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private moyenPaiementService: MoyenPaiementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMoyenPaiements();
    }

    load(id) {
        this.moyenPaiementService.find(id).subscribe((moyenPaiement) => {
            this.moyenPaiement = moyenPaiement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMoyenPaiements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'moyenPaiementListModification',
            (response) => this.load(this.moyenPaiement.id)
        );
    }
}
