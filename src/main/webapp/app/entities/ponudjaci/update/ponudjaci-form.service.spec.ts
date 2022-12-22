import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ponudjaci.test-samples';

import { PonudjaciFormService } from './ponudjaci-form.service';

describe('Ponudjaci Form Service', () => {
  let service: PonudjaciFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PonudjaciFormService);
  });

  describe('Service methods', () => {
    describe('createPonudjaciFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPonudjaciFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nazivPonudjaca: expect.any(Object),
            odgovornoLice: expect.any(Object),
            adresaPonudjaca: expect.any(Object),
            bankaRacun: expect.any(Object),
          })
        );
      });

      it('passing IPonudjaci should create a new form with FormGroup', () => {
        const formGroup = service.createPonudjaciFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nazivPonudjaca: expect.any(Object),
            odgovornoLice: expect.any(Object),
            adresaPonudjaca: expect.any(Object),
            bankaRacun: expect.any(Object),
          })
        );
      });
    });

    describe('getPonudjaci', () => {
      it('should return NewPonudjaci for default Ponudjaci initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPonudjaciFormGroup(sampleWithNewData);

        const ponudjaci = service.getPonudjaci(formGroup) as any;

        expect(ponudjaci).toMatchObject(sampleWithNewData);
      });

      it('should return NewPonudjaci for empty Ponudjaci initial value', () => {
        const formGroup = service.createPonudjaciFormGroup();

        const ponudjaci = service.getPonudjaci(formGroup) as any;

        expect(ponudjaci).toMatchObject({});
      });

      it('should return IPonudjaci', () => {
        const formGroup = service.createPonudjaciFormGroup(sampleWithRequiredData);

        const ponudjaci = service.getPonudjaci(formGroup) as any;

        expect(ponudjaci).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPonudjaci should not enable id FormControl', () => {
        const formGroup = service.createPonudjaciFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPonudjaci should disable id FormControl', () => {
        const formGroup = service.createPonudjaciFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
