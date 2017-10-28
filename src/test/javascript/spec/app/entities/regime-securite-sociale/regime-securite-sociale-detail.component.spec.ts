/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RegimeSecuriteSocialeDetailComponent } from '../../../../../../main/webapp/app/entities/regime-securite-sociale/regime-securite-sociale-detail.component';
import { RegimeSecuriteSocialeService } from '../../../../../../main/webapp/app/entities/regime-securite-sociale/regime-securite-sociale.service';
import { RegimeSecuriteSociale } from '../../../../../../main/webapp/app/entities/regime-securite-sociale/regime-securite-sociale.model';

describe('Component Tests', () => {

    describe('RegimeSecuriteSociale Management Detail Component', () => {
        let comp: RegimeSecuriteSocialeDetailComponent;
        let fixture: ComponentFixture<RegimeSecuriteSocialeDetailComponent>;
        let service: RegimeSecuriteSocialeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [RegimeSecuriteSocialeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RegimeSecuriteSocialeService,
                    JhiEventManager
                ]
            }).overrideTemplate(RegimeSecuriteSocialeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegimeSecuriteSocialeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimeSecuriteSocialeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RegimeSecuriteSociale(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.regimeSecuriteSociale).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
