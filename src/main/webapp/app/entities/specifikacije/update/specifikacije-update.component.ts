import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISpecifikacije, Specifikacije } from '../specifikacije.model';
import { SpecifikacijeService } from '../service/specifikacije.service';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-specifikacije-update',
  templateUrl: './specifikacije-update.component.html',
})
export class SpecifikacijeUpdateComponent implements OnInit {
  isSaving = false;
  @Input() public id: any;
  @Input() public sifraPostupka: any;
  @Input() public brojPartije: any;
  @Input() public atc: any;
  @Input() public inn: any;
  @Input() public farmaceutskiOblikLijeka: any;
  @Input() public karakteristika: any;
  @Input() public jacinaLijeka: any;
  @Input() public trazenaKolicina: any;
  @Input() public pakovanje: any;
  @Input() public jedinicaMjere: any;
  @Input() public procijenjenaVrijednost: any;
  @Input() public jedinicnaCijena: any;
  editForm = this.fb.group({
    id: [],
    sifraPostupka: [null, [Validators.required]],
    brojPartije: [null, [Validators.required]],
    atc: [],
    inn: [],
    farmaceutskiOblikLijeka: [],
    karakteristika: [],
    jacinaLijeka: [],
    trazenaKolicina: [],
    pakovanje: [],
    jedinicaMjere: [],
    procijenjenaVrijednost: [null, [Validators.required]],
    jedinicnaCijena: [null, [Validators.required]],
  });

  constructor(
    protected activeModal: NgbActiveModal,
    protected specifikacijeService: SpecifikacijeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.updateForm();
  }

  previousState(): void {
    this.activeModal.close();
  }

  save(): void {
    this.isSaving = true;
    const specifikacije = this.createFromForm();
    if (specifikacije.id !== undefined) {
      this.subscribeToSaveResponse(this.specifikacijeService.update(specifikacije));
    } else {
      this.subscribeToSaveResponse(this.specifikacijeService.create(specifikacije));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecifikacije>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.activeModal.close();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(): void {
    this.editForm.patchValue({
      id: this.id,
      sifraPostupka: this.sifraPostupka,
      brojPartije: this.brojPartije,
      atc: this.atc,
      inn: this.inn,
      farmaceutskiOblikLijeka: this.farmaceutskiOblikLijeka,
      karakteristika: this.karakteristika,
      jacinaLijeka: this.jacinaLijeka,
      trazenaKolicina: this.trazenaKolicina,
      pakovanje: this.pakovanje,
      jedinicaMjere: this.jedinicaMjere,
      procijenjenaVrijednost: this.procijenjenaVrijednost,
      jedinicnaCijena: this.jedinicnaCijena,
    });
  }

  protected createFromForm(): ISpecifikacije {
    return {
      ...new Specifikacije(),
      id: this.editForm.get(['id'])!.value,
      sifraPostupka: this.editForm.get(['sifraPostupka'])!.value,
      brojPartije: this.editForm.get(['brojPartije'])!.value,
      atc: this.editForm.get(['atc'])!.value,
      inn: this.editForm.get(['inn'])!.value,
      farmaceutskiOblikLijeka: this.editForm.get(['farmaceutskiOblikLijeka'])!.value,
      karakteristika: this.editForm.get(['karakteristika'])!.value,
      jacinaLijeka: this.editForm.get(['jacinaLijeka'])!.value,
      trazenaKolicina: this.editForm.get(['trazenaKolicina'])!.value,
      pakovanje: this.editForm.get(['pakovanje'])!.value,
      jedinicaMjere: this.editForm.get(['jedinicaMjere'])!.value,
      procijenjenaVrijednost: this.editForm.get(['procijenjenaVrijednost'])!.value,
      jedinicnaCijena: this.editForm.get(['jedinicnaCijena'])!.value,
    };
  }
}
