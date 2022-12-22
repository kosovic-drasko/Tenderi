import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPostupci, NewPostupci } from '../postupci.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPostupci for edit and NewPostupciFormGroupInput for create.
 */
type PostupciFormGroupInput = IPostupci | PartialWithRequiredKeyOf<NewPostupci>;

type PostupciFormDefaults = Pick<NewPostupci, 'id'>;

type PostupciFormGroupContent = {
  id: FormControl<IPostupci['id'] | NewPostupci['id']>;
  sifraPostupka: FormControl<IPostupci['sifraPostupka']>;
  brojTendera: FormControl<IPostupci['brojTendera']>;
  opisPostupka: FormControl<IPostupci['opisPostupka']>;
  vrstaPostupka: FormControl<IPostupci['vrstaPostupka']>;
  datumObjave: FormControl<IPostupci['datumObjave']>;
  datumOtvaranja: FormControl<IPostupci['datumOtvaranja']>;
  kriterijumCijena: FormControl<IPostupci['kriterijumCijena']>;
  drugiKriterijum: FormControl<IPostupci['drugiKriterijum']>;
};

export type PostupciFormGroup = FormGroup<PostupciFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PostupciFormService {
  createPostupciFormGroup(postupci: PostupciFormGroupInput = { id: null }): PostupciFormGroup {
    const postupciRawValue = {
      ...this.getFormDefaults(),
      ...postupci,
    };
    return new FormGroup<PostupciFormGroupContent>({
      id: new FormControl(
        { value: postupciRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sifraPostupka: new FormControl(postupciRawValue.sifraPostupka, {
        validators: [Validators.required],
      }),
      brojTendera: new FormControl(postupciRawValue.brojTendera),
      opisPostupka: new FormControl(postupciRawValue.opisPostupka, {
        validators: [Validators.required],
      }),
      vrstaPostupka: new FormControl(postupciRawValue.vrstaPostupka, {
        validators: [Validators.required],
      }),
      datumObjave: new FormControl(postupciRawValue.datumObjave),
      datumOtvaranja: new FormControl(postupciRawValue.datumOtvaranja),
      kriterijumCijena: new FormControl(postupciRawValue.kriterijumCijena, {
        validators: [Validators.required],
      }),
      drugiKriterijum: new FormControl(postupciRawValue.drugiKriterijum, {
        validators: [Validators.required],
      }),
    });
  }

  getPostupci(form: PostupciFormGroup): IPostupci | NewPostupci {
    return form.getRawValue() as IPostupci | NewPostupci;
  }

  resetForm(form: PostupciFormGroup, postupci: PostupciFormGroupInput): void {
    const postupciRawValue = { ...this.getFormDefaults(), ...postupci };
    form.reset(
      {
        ...postupciRawValue,
        id: { value: postupciRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PostupciFormDefaults {
    return {
      id: null,
    };
  }
}
