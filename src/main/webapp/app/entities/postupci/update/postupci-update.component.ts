import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PostupciFormService, PostupciFormGroup } from './postupci-form.service';
import { IPostupci } from '../postupci.model';
import { PostupciService } from '../service/postupci.service';

@Component({
  selector: 'jhi-postupci-update',
  templateUrl: './postupci-update.component.html',
})
export class PostupciUpdateComponent implements OnInit {
  isSaving = false;
  postupci: IPostupci | null = null;

  editForm: PostupciFormGroup = this.postupciFormService.createPostupciFormGroup();

  constructor(
    protected postupciService: PostupciService,
    protected postupciFormService: PostupciFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postupci }) => {
      this.postupci = postupci;
      if (postupci) {
        this.updateForm(postupci);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postupci = this.postupciFormService.getPostupci(this.editForm);
    if (postupci.id !== null) {
      this.subscribeToSaveResponse(this.postupciService.update(postupci));
    } else {
      this.subscribeToSaveResponse(this.postupciService.create(postupci));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostupci>>): void {
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

  protected updateForm(postupci: IPostupci): void {
    this.postupci = postupci;
    this.postupciFormService.resetForm(this.editForm, postupci);
  }
}
