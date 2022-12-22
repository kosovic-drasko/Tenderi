import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SpecifikacijeFormService, SpecifikacijeFormGroup } from './specifikacije-form.service';
import { ISpecifikacije } from '../specifikacije.model';
import { SpecifikacijeService } from '../service/specifikacije.service';

@Component({
  selector: 'jhi-specifikacije-update',
  templateUrl: './specifikacije-update.component.html',
})
export class SpecifikacijeUpdateComponent implements OnInit {
  isSaving = false;
  specifikacije: ISpecifikacije | null = null;

  editForm: SpecifikacijeFormGroup = this.specifikacijeFormService.createSpecifikacijeFormGroup();

  constructor(
    protected specifikacijeService: SpecifikacijeService,
    protected specifikacijeFormService: SpecifikacijeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specifikacije }) => {
      this.specifikacije = specifikacije;
      if (specifikacije) {
        this.updateForm(specifikacije);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specifikacije = this.specifikacijeFormService.getSpecifikacije(this.editForm);
    if (specifikacije.id !== null) {
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
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(specifikacije: ISpecifikacije): void {
    this.specifikacije = specifikacije;
    this.specifikacijeFormService.resetForm(this.editForm, specifikacije);
  }
}
