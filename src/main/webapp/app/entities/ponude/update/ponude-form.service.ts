import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPonude, NewPonude } from '../ponude.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPonude for edit and NewPonudeFormGroupInput for create.
 */
type PonudeFormGroupInput = IPonude | PartialWithRequiredKeyOf<NewPonude>;

type PonudeFormDefaults = Pick<NewPonude, 'id' | 'selected'>;

type PonudeFormGroupContent = {
  id: FormControl<IPonude['id'] | NewPonude['id']>;
  sifraPostupka: FormControl<IPonude['sifraPostupka']>;
  sifraPonude: FormControl<IPonude['sifraPonude']>;
  brojPartije: FormControl<IPonude['brojPartije']>;
  nazivProizvodjaca: FormControl<IPonude['nazivProizvodjaca']>;
  zasticeniNaziv: FormControl<IPonude['zasticeniNaziv']>;
  ponudjenaVrijednost: FormControl<IPonude['ponudjenaVrijednost']>;
  rokIsporuke: FormControl<IPonude['rokIsporuke']>;
  jedinicnaCijena: FormControl<IPonude['jedinicnaCijena']>;
  selected: FormControl<IPonude['selected']>;
  sifraPonudjaca: FormControl<IPonude['sifraPonudjaca']>;
  karakteristika: FormControl<IPonude['karakteristika']>;
};

export type PonudeFormGroup = FormGroup<PonudeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PonudeFormService {
  createPonudeFormGroup(ponude: PonudeFormGroupInput = { id: null }): PonudeFormGroup {
    const ponudeRawValue = {
      ...this.getFormDefaults(),
      ...ponude,
    };
    return new FormGroup<PonudeFormGroupContent>({
      id: new FormControl(
        { value: ponudeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sifraPostupka: new FormControl(ponudeRawValue.sifraPostupka, {
        validators: [Validators.required],
      }),
      sifraPonude: new FormControl(ponudeRawValue.sifraPonude, {
        validators: [Validators.required],
      }),
      brojPartije: new FormControl(ponudeRawValue.brojPartije, {
        validators: [Validators.required],
      }),
      nazivProizvodjaca: new FormControl(ponudeRawValue.nazivProizvodjaca),
      zasticeniNaziv: new FormControl(ponudeRawValue.zasticeniNaziv),
      ponudjenaVrijednost: new FormControl(ponudeRawValue.ponudjenaVrijednost, {
        validators: [Validators.required],
      }),
      rokIsporuke: new FormControl(ponudeRawValue.rokIsporuke),
      jedinicnaCijena: new FormControl(ponudeRawValue.jedinicnaCijena),
      selected: new FormControl(ponudeRawValue.selected),
      sifraPonudjaca: new FormControl(ponudeRawValue.sifraPonudjaca),
      karakteristika: new FormControl(ponudeRawValue.karakteristika),
    });
  }

  getPonude(form: PonudeFormGroup): IPonude | NewPonude {
    return form.getRawValue() as IPonude | NewPonude;
  }

  resetForm(form: PonudeFormGroup, ponude: PonudeFormGroupInput): void {
    const ponudeRawValue = { ...this.getFormDefaults(), ...ponude };
    form.reset(
      {
        ...ponudeRawValue,
        id: { value: ponudeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PonudeFormDefaults {
    return {
      id: null,
      selected: false,
    };
  }
}
