import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PonudjaciFormService } from './ponudjaci-form.service';
import { PonudjaciService } from '../service/ponudjaci.service';
import { IPonudjaci } from '../ponudjaci.model';

import { PonudjaciUpdateComponent } from './ponudjaci-update.component';

describe('Ponudjaci Management Update Component', () => {
  let comp: PonudjaciUpdateComponent;
  let fixture: ComponentFixture<PonudjaciUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ponudjaciFormService: PonudjaciFormService;
  let ponudjaciService: PonudjaciService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PonudjaciUpdateComponent],
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
      .overrideTemplate(PonudjaciUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PonudjaciUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ponudjaciFormService = TestBed.inject(PonudjaciFormService);
    ponudjaciService = TestBed.inject(PonudjaciService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ponudjaci: IPonudjaci = { id: 456 };

      activatedRoute.data = of({ ponudjaci });
      comp.ngOnInit();

      expect(comp.ponudjaci).toEqual(ponudjaci);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPonudjaci>>();
      const ponudjaci = { id: 123 };
      jest.spyOn(ponudjaciFormService, 'getPonudjaci').mockReturnValue(ponudjaci);
      jest.spyOn(ponudjaciService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ponudjaci });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ponudjaci }));
      saveSubject.complete();

      // THEN
      expect(ponudjaciFormService.getPonudjaci).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ponudjaciService.update).toHaveBeenCalledWith(expect.objectContaining(ponudjaci));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPonudjaci>>();
      const ponudjaci = { id: 123 };
      jest.spyOn(ponudjaciFormService, 'getPonudjaci').mockReturnValue({ id: null });
      jest.spyOn(ponudjaciService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ponudjaci: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ponudjaci }));
      saveSubject.complete();

      // THEN
      expect(ponudjaciFormService.getPonudjaci).toHaveBeenCalled();
      expect(ponudjaciService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPonudjaci>>();
      const ponudjaci = { id: 123 };
      jest.spyOn(ponudjaciService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ponudjaci });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ponudjaciService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
