import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Consultation } from './consultation.model';
import { ConsultationService } from './consultation.service';

@Component({
    selector: 'jhi-consultation-detail',
    templateUrl: './consultation-detail.component.html'
})
export class ConsultationDetailComponent implements OnInit, OnDestroy {

    consultation: Consultation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private consultationService: ConsultationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConsultations();
    }

    load(id) {
        this.consultationService.find(id).subscribe((consultation) => {
            this.consultation = consultation;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultationListModification',
            (response) => this.load(this.consultation.id)
        );
    }
}
