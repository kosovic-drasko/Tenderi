import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { IPostupci, Postupci } from '../postupci.model';
import { PostupciService } from '../service/postupci.service';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-postupci-update',
  templateUrl: './postupci-update.component.html',
})
export class PostupciUpdateComponent implements OnInit {
  isSaving = false;

  @Input() public id: any;
  @Input() public sifraPostupka: any;
  @Input() public brojTendera: any;
  @Input() public opisPostupka: any;
  @Input() public vrstaPostupka: any;
  @Input() public datumObjave: any;
  @Input() public datumOtvaranja: any;
  @Input() public kriterijumCijena: any;
  @Input() public drugiKriterijum: any;

  editForm = this.fb.group({
    id: [],
    sifraPostupka: [null, [Validators.required]],
    brojTendera: [],
    opisPostupka: [null, [Validators.required]],
    vrstaPostupka: [null, [Validators.required]],
    datumObjave: [],
    datumOtvaranja: [],
    kriterijumCijena: [null, [Validators.required]],
    drugiKriterijum: [null, [Validators.required]],
  });

  constructor(
    protected postupciService: PostupciService,
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
    const postupci = this.createFromForm();
    if (postupci.id !== undefined) {
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
      brojTendera: this.brojTendera,
      opisPostupka: this.opisPostupka,
      vrstaPostupka: this.vrstaPostupka,
      datumObjave: this.datumObjave,
      datumOtvaranja: this.datumOtvaranja,
      kriterijumCijena: this.kriterijumCijena,
      drugiKriterijum: this.drugiKriterijum,
    });
  }

  protected createFromForm(): IPostupci {
    return {
      ...new Postupci(),
      id: this.editForm.get(['id'])!.value,
      sifraPostupka: this.editForm.get(['sifraPostupka'])!.value,
      brojTendera: this.editForm.get(['brojTendera'])!.value,
      opisPostupka: this.editForm.get(['opisPostupka'])!.value,
      vrstaPostupka: this.editForm.get(['vrstaPostupka'])!.value,
      datumObjave: this.editForm.get(['datumObjave'])!.value,
      datumOtvaranja: this.editForm.get(['datumOtvaranja'])!.value,
      kriterijumCijena: this.editForm.get(['kriterijumCijena'])!.value,
      drugiKriterijum: this.editForm.get(['drugiKriterijum'])!.value,
    };
  }
}
