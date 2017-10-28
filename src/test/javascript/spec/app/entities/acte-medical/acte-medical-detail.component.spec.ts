/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActeMedicalDetailComponent } from '../../../../../../main/webapp/app/entities/acte-medical/acte-medical-detail.component';
import { ActeMedicalService } from '../../../../../../main/webapp/app/entities/acte-medical/acte-medical.service';
import { ActeMedical } from '../../../../../../main/webapp/app/entities/acte-medical/acte-medical.model';

describe('Component Tests', () => {

    describe('ActeMedical Management Detail Component', () => {
        let comp: ActeMedicalDetailComponent;
        let fixture: ComponentFixture<ActeMedicalDetailComponent>;
        let service: ActeMedicalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [ActeMedicalDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActeMedicalService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActeMedicalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActeMedicalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActeMedicalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ActeMedical(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.acteMedical).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
