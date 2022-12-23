import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISpecifikacije, NewSpecifikacije } from '../specifikacije.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISpecifikacije for edit and NewSpecifikacijeFormGroupInput for create.
 */
type SpecifikacijeFormGroupInput = ISpecifikacije | PartialWithRequiredKeyOf<NewSpecifikacije>;

type SpecifikacijeFormDefaults = Pick<NewSpecifikacije, 'id'>;

type SpecifikacijeFormGroupContent = {
  id: FormControl<ISpecifikacije['id'] | NewSpecifikacije['id']>;
  sifraPostupka: FormControl<ISpecifikacije['sifraPostupka']>;
  brojPartije: FormControl<ISpecifikacije['brojPartije']>;
  atc: FormControl<ISpecifikacije['atc']>;
  inn: FormControl<ISpecifikacije['inn']>;
  farmaceutskiOblikLijeka: FormControl<ISpecifikacije['farmaceutskiOblikLijeka']>;
  jacinaLijeka: FormControl<ISpecifikacije['jacinaLijeka']>;
  trazenaKolicina: FormControl<ISpecifikacije['trazenaKolicina']>;
  pakovanje: FormControl<ISpecifikacije['pakovanje']>;
  jedinicaMjere: FormControl<ISpecifikacije['jedinicaMjere']>;
  procijenjenaVrijednost: FormControl<ISpecifikacije['procijenjenaVrijednost']>;
  jedinicnaCijena: FormControl<ISpecifikacije['jedinicnaCijena']>;
  karakteristika: FormControl<ISpecifikacije['karakteristika']>;
};

export type SpecifikacijeFormGroup = FormGroup<SpecifikacijeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SpecifikacijeFormService {
  createSpecifikacijeFormGroup(specifikacije: SpecifikacijeFormGroupInput = { id: null }): SpecifikacijeFormGroup {
    const specifikacijeRawValue = {
      ...this.getFormDefaults(),
      ...specifikacije,
    };
    return new FormGroup<SpecifikacijeFormGroupContent>({
      id: new FormControl(
        { value: specifikacijeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sifraPostupka: new FormControl(specifikacijeRawValue.sifraPostupka, {
        validators: [Validators.required],
      }),
      brojPartije: new FormControl(specifikacijeRawValue.brojPartije, {
        validators: [Validators.required],
      }),
      atc: new FormControl(specifikacijeRawValue.atc),
      inn: new FormControl(specifikacijeRawValue.inn),
      farmaceutskiOblikLijeka: new FormControl(specifikacijeRawValue.farmaceutskiOblikLijeka),
      jacinaLijeka: new FormControl(specifikacijeRawValue.jacinaLijeka),
      trazenaKolicina: new FormControl(specifikacijeRawValue.trazenaKolicina),
      pakovanje: new FormControl(specifikacijeRawValue.pakovanje),
      jedinicaMjere: new FormControl(specifikacijeRawValue.jedinicaMjere),
      procijenjenaVrijednost: new FormControl(specifikacijeRawValue.procijenjenaVrijednost, {
        validators: [Validators.required],
      }),
      jedinicnaCijena: new FormControl(specifikacijeRawValue.jedinicnaCijena, {
        validators: [Validators.required],
      }),
      karakteristika: new FormControl(specifikacijeRawValue.karakteristika),
    });
  }

  getSpecifikacije(form: SpecifikacijeFormGroup): ISpecifikacije | NewSpecifikacije {
    return form.getRawValue() as ISpecifikacije | NewSpecifikacije;
  }

  resetForm(form: SpecifikacijeFormGroup, specifikacije: SpecifikacijeFormGroupInput): void {
    const specifikacijeRawValue = { ...this.getFormDefaults(), ...specifikacije };
    form.reset(
      {
        ...specifikacijeRawValue,
        id: { value: specifikacijeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SpecifikacijeFormDefaults {
    return {
      id: null,
    };
  }
}
