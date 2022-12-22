import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IViewPonude, NewViewPonude } from '../view-ponude.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IViewPonude for edit and NewViewPonudeFormGroupInput for create.
 */
type ViewPonudeFormGroupInput = IViewPonude | PartialWithRequiredKeyOf<NewViewPonude>;

type ViewPonudeFormDefaults = Pick<NewViewPonude, 'id' | 'selected'>;

type ViewPonudeFormGroupContent = {
  id: FormControl<IViewPonude['id'] | NewViewPonude['id']>;
  sifraPostupka: FormControl<IViewPonude['sifraPostupka']>;
  sifraPonude: FormControl<IViewPonude['sifraPonude']>;
  brojPartije: FormControl<IViewPonude['brojPartije']>;
  nazivProizvodjaca: FormControl<IViewPonude['nazivProizvodjaca']>;
  zasticeniNaziv: FormControl<IViewPonude['zasticeniNaziv']>;
  ponudjenaVrijednost: FormControl<IViewPonude['ponudjenaVrijednost']>;
  rokIsporuke: FormControl<IViewPonude['rokIsporuke']>;
  jedinicnaCijena: FormControl<IViewPonude['jedinicnaCijena']>;
  selected: FormControl<IViewPonude['selected']>;
  nazivPonudjaca: FormControl<IViewPonude['nazivPonudjaca']>;
  karakteristika: FormControl<IViewPonude['karakteristika']>;
};

export type ViewPonudeFormGroup = FormGroup<ViewPonudeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ViewPonudeFormService {
  createViewPonudeFormGroup(viewPonude: ViewPonudeFormGroupInput = { id: null }): ViewPonudeFormGroup {
    const viewPonudeRawValue = {
      ...this.getFormDefaults(),
      ...viewPonude,
    };
    return new FormGroup<ViewPonudeFormGroupContent>({
      id: new FormControl(
        { value: viewPonudeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sifraPostupka: new FormControl(viewPonudeRawValue.sifraPostupka, {
        validators: [Validators.required],
      }),
      sifraPonude: new FormControl(viewPonudeRawValue.sifraPonude, {
        validators: [Validators.required],
      }),
      brojPartije: new FormControl(viewPonudeRawValue.brojPartije, {
        validators: [Validators.required],
      }),
      nazivProizvodjaca: new FormControl(viewPonudeRawValue.nazivProizvodjaca),
      zasticeniNaziv: new FormControl(viewPonudeRawValue.zasticeniNaziv),
      ponudjenaVrijednost: new FormControl(viewPonudeRawValue.ponudjenaVrijednost, {
        validators: [Validators.required],
      }),
      rokIsporuke: new FormControl(viewPonudeRawValue.rokIsporuke),
      jedinicnaCijena: new FormControl(viewPonudeRawValue.jedinicnaCijena),
      selected: new FormControl(viewPonudeRawValue.selected),
      nazivPonudjaca: new FormControl(viewPonudeRawValue.nazivPonudjaca),
      karakteristika: new FormControl(viewPonudeRawValue.karakteristika),
    });
  }

  getViewPonude(form: ViewPonudeFormGroup): IViewPonude | NewViewPonude {
    return form.getRawValue() as IViewPonude | NewViewPonude;
  }

  resetForm(form: ViewPonudeFormGroup, viewPonude: ViewPonudeFormGroupInput): void {
    const viewPonudeRawValue = { ...this.getFormDefaults(), ...viewPonude };
    form.reset(
      {
        ...viewPonudeRawValue,
        id: { value: viewPonudeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ViewPonudeFormDefaults {
    return {
      id: null,
      selected: false,
    };
  }
}
