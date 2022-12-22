import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../specifikacije.test-samples';

import { SpecifikacijeFormService } from './specifikacije-form.service';

describe('Specifikacije Form Service', () => {
  let service: SpecifikacijeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecifikacijeFormService);
  });

  describe('Service methods', () => {
    describe('createSpecifikacijeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSpecifikacijeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            brojPartije: expect.any(Object),
            atc: expect.any(Object),
            inn: expect.any(Object),
            farmaceutskiOblikLijeka: expect.any(Object),
            jacinaLijeka: expect.any(Object),
            trazenaKolicina: expect.any(Object),
            pakovanje: expect.any(Object),
            jedinicaMjere: expect.any(Object),
            procijenjenaVrijednost: expect.any(Object),
            jedinicnaCijena: expect.any(Object),
          })
        );
      });

      it('passing ISpecifikacije should create a new form with FormGroup', () => {
        const formGroup = service.createSpecifikacijeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            brojPartije: expect.any(Object),
            atc: expect.any(Object),
            inn: expect.any(Object),
            farmaceutskiOblikLijeka: expect.any(Object),
            jacinaLijeka: expect.any(Object),
            trazenaKolicina: expect.any(Object),
            pakovanje: expect.any(Object),
            jedinicaMjere: expect.any(Object),
            procijenjenaVrijednost: expect.any(Object),
            jedinicnaCijena: expect.any(Object),
          })
        );
      });
    });

    describe('getSpecifikacije', () => {
      it('should return NewSpecifikacije for default Specifikacije initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSpecifikacijeFormGroup(sampleWithNewData);

        const specifikacije = service.getSpecifikacije(formGroup) as any;

        expect(specifikacije).toMatchObject(sampleWithNewData);
      });

      it('should return NewSpecifikacije for empty Specifikacije initial value', () => {
        const formGroup = service.createSpecifikacijeFormGroup();

        const specifikacije = service.getSpecifikacije(formGroup) as any;

        expect(specifikacije).toMatchObject({});
      });

      it('should return ISpecifikacije', () => {
        const formGroup = service.createSpecifikacijeFormGroup(sampleWithRequiredData);

        const specifikacije = service.getSpecifikacije(formGroup) as any;

        expect(specifikacije).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISpecifikacije should not enable id FormControl', () => {
        const formGroup = service.createSpecifikacijeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSpecifikacije should disable id FormControl', () => {
        const formGroup = service.createSpecifikacijeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
