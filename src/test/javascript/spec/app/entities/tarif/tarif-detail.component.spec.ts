/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TarifDetailComponent } from '../../../../../../main/webapp/app/entities/tarif/tarif-detail.component';
import { TarifService } from '../../../../../../main/webapp/app/entities/tarif/tarif.service';
import { Tarif } from '../../../../../../main/webapp/app/entities/tarif/tarif.model';

describe('Component Tests', () => {

    describe('Tarif Management Detail Component', () => {
        let comp: TarifDetailComponent;
        let fixture: ComponentFixture<TarifDetailComponent>;
        let service: TarifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [TarifDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TarifService,
                    JhiEventManager
                ]
            }).overrideTemplate(TarifDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TarifDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TarifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Tarif(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tarif).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
