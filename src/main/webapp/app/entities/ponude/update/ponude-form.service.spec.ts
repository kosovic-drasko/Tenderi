import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ponude.test-samples';

import { PonudeFormService } from './ponude-form.service';

describe('Ponude Form Service', () => {
  let service: PonudeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PonudeFormService);
  });

  describe('Service methods', () => {
    describe('createPonudeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPonudeFormGroup();

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
            sifraPonudjaca: expect.any(Object),
            karakteristika: expect.any(Object),
          })
        );
      });

      it('passing IPonude should create a new form with FormGroup', () => {
        const formGroup = service.createPonudeFormGroup(sampleWithRequiredData);

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
            sifraPonudjaca: expect.any(Object),
            karakteristika: expect.any(Object),
          })
        );
      });
    });

    describe('getPonude', () => {
      it('should return NewPonude for default Ponude initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPonudeFormGroup(sampleWithNewData);

        const ponude = service.getPonude(formGroup) as any;

        expect(ponude).toMatchObject(sampleWithNewData);
      });

      it('should return NewPonude for empty Ponude initial value', () => {
        const formGroup = service.createPonudeFormGroup();

        const ponude = service.getPonude(formGroup) as any;

        expect(ponude).toMatchObject({});
      });

      it('should return IPonude', () => {
        const formGroup = service.createPonudeFormGroup(sampleWithRequiredData);

        const ponude = service.getPonude(formGroup) as any;

        expect(ponude).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPonude should not enable id FormControl', () => {
        const formGroup = service.createPonudeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPonude should disable id FormControl', () => {
        const formGroup = service.createPonudeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
