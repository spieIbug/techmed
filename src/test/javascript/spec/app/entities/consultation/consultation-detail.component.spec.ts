/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultationDetailComponent } from '../../../../../../main/webapp/app/entities/consultation/consultation-detail.component';
import { ConsultationService } from '../../../../../../main/webapp/app/entities/consultation/consultation.service';
import { Consultation } from '../../../../../../main/webapp/app/entities/consultation/consultation.model';

describe('Component Tests', () => {

    describe('Consultation Management Detail Component', () => {
        let comp: ConsultationDetailComponent;
        let fixture: ComponentFixture<ConsultationDetailComponent>;
        let service: ConsultationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [ConsultationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultationService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Consultation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
