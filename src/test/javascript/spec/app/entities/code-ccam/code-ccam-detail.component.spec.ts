/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TechmedTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CodeCCAMDetailComponent } from '../../../../../../main/webapp/app/entities/code-ccam/code-ccam-detail.component';
import { CodeCCAMService } from '../../../../../../main/webapp/app/entities/code-ccam/code-ccam.service';
import { CodeCCAM } from '../../../../../../main/webapp/app/entities/code-ccam/code-ccam.model';

describe('Component Tests', () => {

    describe('CodeCCAM Management Detail Component', () => {
        let comp: CodeCCAMDetailComponent;
        let fixture: ComponentFixture<CodeCCAMDetailComponent>;
        let service: CodeCCAMService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TechmedTestModule],
                declarations: [CodeCCAMDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CodeCCAMService,
                    JhiEventManager
                ]
            }).overrideTemplate(CodeCCAMDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CodeCCAMDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CodeCCAMService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CodeCCAM(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.codeCCAM).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
