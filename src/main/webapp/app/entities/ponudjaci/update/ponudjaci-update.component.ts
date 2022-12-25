import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPonudjaci, Ponudjaci } from '../ponudjaci.model';
import { PonudjaciService } from '../service/ponudjaci.service';
import { FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-ponudjaci-update',
  templateUrl: './ponudjaci-update.component.html',
})
export class PonudjaciUpdateComponent implements OnInit {
  isSaving = false;
  @Input() public id: any;
  @Input() public nazivPonudjaca: any;
  @Input() public odgovornoLice: any;
  @Input() public adresaPonudjaca: any;
  @Input() public bankaRacun: any;
  editForm = this.fb.group({
    id: [],
    nazivPonudjaca: [],
    odgovornoLice: [],
    adresaPonudjaca: [],
    bankaRacun: [],
  });

  constructor(
    protected ponudjaciService: PonudjaciService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.updateForm();
  }

  previousState(): void {
    this.activeModal.close();
  }

  save(): void {
    this.isSaving = true;
    const ponudjaci = this.createFromForm();
    if (ponudjaci.id !== undefined) {
      this.subscribeToSaveResponse(this.ponudjaciService.update(ponudjaci));
    } else {
      this.subscribeToSaveResponse(this.ponudjaciService.create(ponudjaci));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPonudjaci>>): void {
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
      nazivPonudjaca: this.nazivPonudjaca,
      odgovornoLice: this.odgovornoLice,
      adresaPonudjaca: this.adresaPonudjaca,
      bankaRacun: this.bankaRacun,
    });
  }

  protected createFromForm(): IPonudjaci {
    return {
      ...new Ponudjaci(),
      id: this.editForm.get(['id'])!.value,
      nazivPonudjaca: this.editForm.get(['nazivPonudjaca'])!.value,
      odgovornoLice: this.editForm.get(['odgovornoLice'])!.value,
      adresaPonudjaca: this.editForm.get(['adresaPonudjaca'])!.value,
      bankaRacun: this.editForm.get(['bankaRacun'])!.value,
    };
  }
}
