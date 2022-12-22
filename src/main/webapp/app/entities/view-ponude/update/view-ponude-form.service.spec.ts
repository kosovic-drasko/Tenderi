import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../view-ponude.test-samples';

import { ViewPonudeFormService } from './view-ponude-form.service';

describe('ViewPonude Form Service', () => {
  let service: ViewPonudeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewPonudeFormService);
  });

  describe('Service methods', () => {
    describe('createViewPonudeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createViewPonudeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            sifraPonude: expect.any(Object),
            brojPartije: expect.any(Object),
            nazivProizvodjaca: expect.any(Object),
            zasticeniNaziv: expect.any(Object),
            ponudjenaVrijednost: expect.any(Object),
            rokIsporuke: expect.any(Object),
            jedinicnaCijena: expect.any(Object),
            selected: expect.any(Object),
            nazivPonudjaca: expect.any(Object),
            karakteristika: expect.any(Object),
          })
        );
      });

      it('passing IViewPonude should create a new form with FormGroup', () => {
        const formGroup = service.createViewPonudeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            sifraPonude: expect.any(Object),
            brojPartije: expect.any(Object),
            nazivProizvodjaca: expect.any(Object),
            zasticeniNaziv: expect.any(Object),
            ponudjenaVrijednost: expect.any(Object),
            rokIsporuke: expect.any(Object),
            jedinicnaCijena: expect.any(Object),
            selected: expect.any(Object),
            nazivPonudjaca: expect.any(Object),
            karakteristika: expect.any(Object),
          })
        );
      });
    });

    describe('getViewPonude', () => {
      it('should return NewViewPonude for default ViewPonude initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createViewPonudeFormGroup(sampleWithNewData);

        const viewPonude = service.getViewPonude(formGroup) as any;

        expect(viewPonude).toMatchObject(sampleWithNewData);
      });

      it('should return NewViewPonude for empty ViewPonude initial value', () => {
        const formGroup = service.createViewPonudeFormGroup();

        const viewPonude = service.getViewPonude(formGroup) as any;

        expect(viewPonude).toMatchObject({});
      });

      it('should return IViewPonude', () => {
        const formGroup = service.createViewPonudeFormGroup(sampleWithRequiredData);

        const viewPonude = service.getViewPonude(formGroup) as any;

        expect(viewPonude).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IViewPonude should not enable id FormControl', () => {
        const formGroup = service.createViewPonudeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewViewPonude should disable id FormControl', () => {
        const formGroup = service.createViewPonudeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
