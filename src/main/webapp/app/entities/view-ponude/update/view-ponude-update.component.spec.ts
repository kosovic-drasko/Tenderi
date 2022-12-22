import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ViewPonudeFormService } from './view-ponude-form.service';
import { ViewPonudeService } from '../service/view-ponude.service';
import { IViewPonude } from '../view-ponude.model';

import { ViewPonudeUpdateComponent } from './view-ponude-update.component';

describe('ViewPonude Management Update Component', () => {
  let comp: ViewPonudeUpdateComponent;
  let fixture: ComponentFixture<ViewPonudeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let viewPonudeFormService: ViewPonudeFormService;
  let viewPonudeService: ViewPonudeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ViewPonudeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ViewPonudeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ViewPonudeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    viewPonudeFormService = TestBed.inject(ViewPonudeFormService);
    viewPonudeService = TestBed.inject(ViewPonudeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const viewPonude: IViewPonude = { id: 456 };

      activatedRoute.data = of({ viewPonude });
      comp.ngOnInit();

      expect(comp.viewPonude).toEqual(viewPonude);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IViewPonude>>();
      const viewPonude = { id: 123 };
      jest.spyOn(viewPonudeFormService, 'getViewPonude').mockReturnValue(viewPonude);
      jest.spyOn(viewPonudeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ viewPonude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: viewPonude }));
      saveSubject.complete();

      // THEN
      expect(viewPonudeFormService.getViewPonude).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(viewPonudeService.update).toHaveBeenCalledWith(expect.objectContaining(viewPonude));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IViewPonude>>();
      const viewPonude = { id: 123 };
      jest.spyOn(viewPonudeFormService, 'getViewPonude').mockReturnValue({ id: null });
      jest.spyOn(viewPonudeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ viewPonude: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: viewPonude }));
      saveSubject.complete();

      // THEN
      expect(viewPonudeFormService.getViewPonude).toHaveBeenCalled();
      expect(viewPonudeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IViewPonude>>();
      const viewPonude = { id: 123 };
      jest.spyOn(viewPonudeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ viewPonude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(viewPonudeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
