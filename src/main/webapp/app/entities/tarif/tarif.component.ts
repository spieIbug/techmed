import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Tarif } from './tarif.model';
import { TarifService } from './tarif.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tarif',
    templateUrl: './tarif.component.html'
})
export class TarifComponent implements OnInit, OnDestroy {
tarifs: Tarif[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tarifService: TarifService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tarifService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tarifs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTarifs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Tarif) {
        return item.id;
    }
    registerChangeInTarifs() {
        this.eventSubscriber = this.eventManager.subscribe('tarifListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
