import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Tarif } from './tarif.model';
import { TarifService } from './tarif.service';

@Component({
    selector: 'jhi-tarif-detail',
    templateUrl: './tarif-detail.component.html'
})
export class TarifDetailComponent implements OnInit, OnDestroy {

    tarif: Tarif;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tarifService: TarifService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTarifs();
    }

    load(id) {
        this.tarifService.find(id).subscribe((tarif) => {
            this.tarif = tarif;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTarifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tarifListModification',
            (response) => this.load(this.tarif.id)
        );
    }
}
