import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { IPonude, Ponude } from '../ponude.model';
import { PonudeService } from '../service/ponude.service';
import { IPonudjaci } from '../../ponudjaci/ponudjaci.model';
import { FormBuilder, Validators } from '@angular/forms';
import { PonudjaciService } from '../../ponudjaci/service/ponudjaci.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-ponude-update',
  templateUrl: './ponude-update.component.html',
})
export class PonudeUpdateComponent implements OnInit {
  isSaving = false;
  ponudjaci: IPonudjaci[] = [];

  ponudes: IPonude[] = [];

  @Input() public dialog: any;
  @Input() public id: any;
  @Input() public sifraPostupka: any;
  @Input() public sifraPonude: any;
  @Input() public brojPartije: any;
  @Input() public sifraPonudjaca: any;
  @Input() public nazivProizvodjaca: any;
  @Input() public zasticeniNaziv: any;
  @Input() public karakteristika: any;
  @Input() public ponudjenaVrijednost: any;
  @Input() public jedinicnaCijena: any;
  @Input() public selected: any;
  @Input() public rokIsporuke: any;
  editForm = this.fb.group({
    id: [],
    sifraPostupka: [null, [Validators.required]],
    sifraPonude: [null, [Validators.required]],
    brojPartije: [null, [Validators.required]],
    nazivProizvodjaca: [],
    zasticeniNaziv: [],
    karakteristika: [],
    ponudjenaVrijednost: [null, [Validators.required]],
    rokIsporuke: [],
    jedinicnaCijena: [],
    selected: [],
    sifraPonudjaca: [],
  });

  constructor(
    protected ponudeService: PonudeService,
    protected ponudjaciService: PonudjaciService,
    protected activeModal: NgbActiveModal,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    console.log('to je karakteristika', this.karakteristika);
    console.log('to je naziv', this.nazivProizvodjaca);
    console.log('to je sifra', this.sifraPonudjaca);
    console.log('to je zasticeniNaziv', this.zasticeniNaziv);
    this.updateForm();
    this.loadAllPonudjaci();
  }

  loadAllPonudjaci(): void {
    this.ponudjaciService.query().subscribe((res: HttpResponse<IPonudjaci[]>) => {
      this.ponudjaci = res.body ?? [];
    });
  }

  previousState(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const ponude = this.createFromForm();
    if (ponude.id !== undefined) {
      this.subscribeToSaveResponse(this.ponudeService.update(ponude));
    } else {
      this.subscribeToSaveResponse(this.ponudeService.create(ponude));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPonude>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.activeModal.close();
  }

  close(): any {
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
      sifraPonude: this.sifraPonude,
      brojPartije: this.brojPartije,
      nazivProizvodjaca: this.nazivProizvodjaca,
      zasticeniNaziv: this.zasticeniNaziv,
      karakteristika: this.karakteristika,
      ponudjenaVrijednost: this.ponudjenaVrijednost,
      rokIsporuke: this.rokIsporuke,
      jedinicnaCijena: this.jedinicnaCijena,
      selected: this.selected,
      sifraPonudjaca: this.sifraPonudjaca,
    });
  }
  protected createFromForm(): IPonude {
    return {
      ...new Ponude(),
      id: this.editForm.get(['id'])!.value,
      sifraPostupka: this.editForm.get(['sifraPostupka'])!.value,
      sifraPonude: this.editForm.get(['sifraPonude'])!.value,
      brojPartije: this.editForm.get(['brojPartije'])!.value,
      nazivProizvodjaca: this.editForm.get(['nazivProizvodjaca'])!.value,
      zasticeniNaziv: this.editForm.get(['zasticeniNaziv'])!.value,
      karakteristika: this.editForm.get(['karakteristika'])!.value,
      ponudjenaVrijednost: this.editForm.get(['ponudjenaVrijednost'])!.value,
      rokIsporuke: this.editForm.get(['rokIsporuke'])!.value,
      jedinicnaCijena: this.editForm.get(['jedinicnaCijena'])!.value,
      selected: this.editForm.get(['selected'])!.value,
      sifraPonudjaca: this.editForm.get(['sifraPonudjaca'])!.value,
    };
  }
}
