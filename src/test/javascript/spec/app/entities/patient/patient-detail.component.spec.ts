/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PatientDetailComponent } from '../../../../../../main/webapp/app/entities/patient/patient-detail.component';
import { PatientService } from '../../../../../../main/webapp/app/entities/patient/patient.service';
import { Patient } from '../../../../../../main/webapp/app/entities/patient/patient.model';

describe('Component Tests', () => {

    describe('Patient Management Detail Component', () => {
        let comp: PatientDetailComponent;
        let fixture: ComponentFixture<PatientDetailComponent>;
        let service: PatientService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [PatientDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PatientService,
                    JhiEventManager
                ]
            }).overrideTemplate(PatientDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatientDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatientService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Patient(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.patient).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
