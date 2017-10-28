/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaiementDetailComponent } from '../../../../../../main/webapp/app/entities/paiement/paiement-detail.component';
import { PaiementService } from '../../../../../../main/webapp/app/entities/paiement/paiement.service';
import { Paiement } from '../../../../../../main/webapp/app/entities/paiement/paiement.model';

describe('Component Tests', () => {

    describe('Paiement Management Detail Component', () => {
        let comp: PaiementDetailComponent;
        let fixture: ComponentFixture<PaiementDetailComponent>;
        let service: PaiementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [PaiementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaiementService,
                    JhiEventManager
                ]
            }).overrideTemplate(PaiementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Paiement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.paiement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
