import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../postupci.test-samples';

import { PostupciFormService } from './postupci-form.service';

describe('Postupci Form Service', () => {
  let service: PostupciFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostupciFormService);
  });

  describe('Service methods', () => {
    describe('createPostupciFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPostupciFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            brojTendera: expect.any(Object),
            opisPostupka: expect.any(Object),
            vrstaPostupka: expect.any(Object),
            datumObjave: expect.any(Object),
            datumOtvaranja: expect.any(Object),
            kriterijumCijena: expect.any(Object),
            drugiKriterijum: expect.any(Object),
          })
        );
      });

      it('passing IPostupci should create a new form with FormGroup', () => {
        const formGroup = service.createPostupciFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sifraPostupka: expect.any(Object),
            brojTendera: expect.any(Object),
            opisPostupka: expect.any(Object),
            vrstaPostupka: expect.any(Object),
            datumObjave: expect.any(Object),
            datumOtvaranja: expect.any(Object),
            kriterijumCijena: expect.any(Object),
            drugiKriterijum: expect.any(Object),
          })
        );
      });
    });

    describe('getPostupci', () => {
      it('should return NewPostupci for default Postupci initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPostupciFormGroup(sampleWithNewData);

        const postupci = service.getPostupci(formGroup) as any;

        expect(postupci).toMatchObject(sampleWithNewData);
      });

      it('should return NewPostupci for empty Postupci initial value', () => {
        const formGroup = service.createPostupciFormGroup();

        const postupci = service.getPostupci(formGroup) as any;

        expect(postupci).toMatchObject({});
      });

      it('should return IPostupci', () => {
        const formGroup = service.createPostupciFormGroup(sampleWithRequiredData);

        const postupci = service.getPostupci(formGroup) as any;

        expect(postupci).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPostupci should not enable id FormControl', () => {
        const formGroup = service.createPostupciFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPostupci should disable id FormControl', () => {
        const formGroup = service.createPostupciFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
