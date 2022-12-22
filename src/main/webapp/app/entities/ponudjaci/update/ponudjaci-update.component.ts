import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PonudjaciFormService, PonudjaciFormGroup } from './ponudjaci-form.service';
import { IPonudjaci } from '../ponudjaci.model';
import { PonudjaciService } from '../service/ponudjaci.service';

@Component({
  selector: 'jhi-ponudjaci-update',
  templateUrl: './ponudjaci-update.component.html',
})
export class PonudjaciUpdateComponent implements OnInit {
  isSaving = false;
  ponudjaci: IPonudjaci | null = null;

  editForm: PonudjaciFormGroup = this.ponudjaciFormService.createPonudjaciFormGroup();

  constructor(
    protected ponudjaciService: PonudjaciService,
    protected ponudjaciFormService: PonudjaciFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ponudjaci }) => {
      this.ponudjaci = ponudjaci;
      if (ponudjaci) {
        this.updateForm(ponudjaci);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ponudjaci = this.ponudjaciFormService.getPonudjaci(this.editForm);
    if (ponudjaci.id !== null) {
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
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(ponudjaci: IPonudjaci): void {
    this.ponudjaci = ponudjaci;
    this.ponudjaciFormService.resetForm(this.editForm, ponudjaci);
  }
}
