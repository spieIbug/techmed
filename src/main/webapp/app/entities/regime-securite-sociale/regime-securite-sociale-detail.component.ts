import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RegimeSecuriteSociale } from './regime-securite-sociale.model';
import { RegimeSecuriteSocialeService } from './regime-securite-sociale.service';

@Component({
    selector: 'jhi-regime-securite-sociale-detail',
    templateUrl: './regime-securite-sociale-detail.component.html'
})
export class RegimeSecuriteSocialeDetailComponent implements OnInit, OnDestroy {

    regimeSecuriteSociale: RegimeSecuriteSociale;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private regimeSecuriteSocialeService: RegimeSecuriteSocialeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRegimeSecuriteSociales();
    }

    load(id) {
        this.regimeSecuriteSocialeService.find(id).subscribe((regimeSecuriteSociale) => {
            this.regimeSecuriteSociale = regimeSecuriteSociale;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRegimeSecuriteSociales() {
        this.eventSubscriber = this.eventManager.subscribe(
            'regimeSecuriteSocialeListModification',
            (response) => this.load(this.regimeSecuriteSociale.id)
        );
    }
}
