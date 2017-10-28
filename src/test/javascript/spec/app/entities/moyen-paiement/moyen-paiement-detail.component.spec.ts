/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MoyenPaiementDetailComponent } from '../../../../../../main/webapp/app/entities/moyen-paiement/moyen-paiement-detail.component';
import { MoyenPaiementService } from '../../../../../../main/webapp/app/entities/moyen-paiement/moyen-paiement.service';
import { MoyenPaiement } from '../../../../../../main/webapp/app/entities/moyen-paiement/moyen-paiement.model';

describe('Component Tests', () => {

    describe('MoyenPaiement Management Detail Component', () => {
        let comp: MoyenPaiementDetailComponent;
        let fixture: ComponentFixture<MoyenPaiementDetailComponent>;
        let service: MoyenPaiementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [MoyenPaiementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MoyenPaiementService,
                    JhiEventManager
                ]
            }).overrideTemplate(MoyenPaiementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoyenPaiementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoyenPaiementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MoyenPaiement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.moyenPaiement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
