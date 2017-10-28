import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CodeCCAM } from './code-ccam.model';
import { CodeCCAMService } from './code-ccam.service';

@Component({
    selector: 'jhi-code-ccam-detail',
    templateUrl: './code-ccam-detail.component.html'
})
export class CodeCCAMDetailComponent implements OnInit, OnDestroy {

    codeCCAM: CodeCCAM;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private codeCCAMService: CodeCCAMService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCodeCCAMS();
    }

    load(id) {
        this.codeCCAMService.find(id).subscribe((codeCCAM) => {
            this.codeCCAM = codeCCAM;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCodeCCAMS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'codeCCAMListModification',
            (response) => this.load(this.codeCCAM.id)
        );
    }
}
