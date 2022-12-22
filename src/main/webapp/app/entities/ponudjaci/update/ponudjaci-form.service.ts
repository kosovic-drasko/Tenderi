import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPonudjaci, NewPonudjaci } from '../ponudjaci.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPonudjaci for edit and NewPonudjaciFormGroupInput for create.
 */
type PonudjaciFormGroupInput = IPonudjaci | PartialWithRequiredKeyOf<NewPonudjaci>;

type PonudjaciFormDefaults = Pick<NewPonudjaci, 'id'>;

type PonudjaciFormGroupContent = {
  id: FormControl<IPonudjaci['id'] | NewPonudjaci['id']>;
  nazivPonudjaca: FormControl<IPonudjaci['nazivPonudjaca']>;
  odgovornoLice: FormControl<IPonudjaci['odgovornoLice']>;
  adresaPonudjaca: FormControl<IPonudjaci['adresaPonudjaca']>;
  bankaRacun: FormControl<IPonudjaci['bankaRacun']>;
};

export type PonudjaciFormGroup = FormGroup<PonudjaciFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PonudjaciFormService {
  createPonudjaciFormGroup(ponudjaci: PonudjaciFormGroupInput = { id: null }): PonudjaciFormGroup {
    const ponudjaciRawValue = {
      ...this.getFormDefaults(),
      ...ponudjaci,
    };
    return new FormGroup<PonudjaciFormGroupContent>({
      id: new FormControl(
        { value: ponudjaciRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nazivPonudjaca: new FormControl(ponudjaciRawValue.nazivPonudjaca),
      odgovornoLice: new FormControl(ponudjaciRawValue.odgovornoLice),
      adresaPonudjaca: new FormControl(ponudjaciRawValue.adresaPonudjaca),
      bankaRacun: new FormControl(ponudjaciRawValue.bankaRacun),
    });
  }

  getPonudjaci(form: PonudjaciFormGroup): IPonudjaci | NewPonudjaci {
    return form.getRawValue() as IPonudjaci | NewPonudjaci;
  }

  resetForm(form: PonudjaciFormGroup, ponudjaci: PonudjaciFormGroupInput): void {
    const ponudjaciRawValue = { ...this.getFormDefaults(), ...ponudjaci };
    form.reset(
      {
        ...ponudjaciRawValue,
        id: { value: ponudjaciRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PonudjaciFormDefaults {
    return {
      id: null,
    };
  }
}
