import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ViewPonudeFormService, ViewPonudeFormGroup } from './view-ponude-form.service';
import { IViewPonude } from '../view-ponude.model';
import { ViewPonudeService } from '../service/view-ponude.service';

@Component({
  selector: 'jhi-view-ponude-update',
  templateUrl: './view-ponude-update.component.html',
})
export class ViewPonudeUpdateComponent implements OnInit {
  isSaving = false;
  viewPonude: IViewPonude | null = null;

  editForm: ViewPonudeFormGroup = this.viewPonudeFormService.createViewPonudeFormGroup();

  constructor(
    protected viewPonudeService: ViewPonudeService,
    protected viewPonudeFormService: ViewPonudeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viewPonude }) => {
      this.viewPonude = viewPonude;
      if (viewPonude) {
        this.updateForm(viewPonude);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const viewPonude = this.viewPonudeFormService.getViewPonude(this.editForm);
    if (viewPonude.id !== null) {
      this.subscribeToSaveResponse(this.viewPonudeService.update(viewPonude));
    } else {
      this.subscribeToSaveResponse(this.viewPonudeService.create(viewPonude));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IViewPonude>>): void {
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

  protected updateForm(viewPonude: IViewPonude): void {
    this.viewPonude = viewPonude;
    this.viewPonudeFormService.resetForm(this.editForm, viewPonude);
  }
}
