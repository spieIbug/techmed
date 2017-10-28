import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ActeMedical } from './acte-medical.model';
import { ActeMedicalService } from './acte-medical.service';

@Component({
    selector: 'jhi-acte-medical-detail',
    templateUrl: './acte-medical-detail.component.html'
})
export class ActeMedicalDetailComponent implements OnInit, OnDestroy {

    acteMedical: ActeMedical;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private acteMedicalService: ActeMedicalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActeMedicals();
    }

    load(id) {
        this.acteMedicalService.find(id).subscribe((acteMedical) => {
            this.acteMedical = acteMedical;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActeMedicals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'acteMedicalListModification',
            (response) => this.load(this.acteMedical.id)
        );
    }
}
