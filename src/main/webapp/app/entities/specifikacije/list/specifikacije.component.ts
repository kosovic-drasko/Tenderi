import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecifikacije } from '../specifikacije.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, SpecifikacijeService } from '../service/specifikacije.service';
import { SpecifikacijeDeleteDialogComponent } from '../delete/specifikacije-delete-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';
import { TableUtil } from '../../../tableUtil';
import { SpecifikacijeUpdateComponent } from '../update/specifikacije-update.component';

@Component({
  selector: 'jhi-specifikacije',
  templateUrl: './specifikacije.component.html',
})
export class SpecifikacijeComponent implements OnInit {
  public resourceUrlExcelDownload = SERVER_API_URL + 'api/specifikacije/file';
  specifikacijes?: ISpecifikacije[];
  isLoading = false;
  predicate = 'id';
  ascending = true;
  message: string | undefined;

  @ViewChild('fileInput') fileInput: any;
  @Input() postupak: any;
  constructor(
    protected specifikacijeService: SpecifikacijeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: ISpecifikacije): number => this.specifikacijeService.getSpecifikacijeIdentifier(item);

  ngOnInit(): void {
    if (this.postupak !== undefined) {
      this.loadSifraPostupka();
    } else {
      this.load();
      console.log('Postupak je >>>>>>>>', this.postupak);
    }
  }

  delete(specifikacije: ISpecifikacije): void {
    const modalRef = this.modalService.open(SpecifikacijeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.specifikacije = specifikacije;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }
  loadSifraPostupka(): void {
    this.loadFromBackendWithRouteInformationsPostupak().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }
  protected loadFromBackendWithRouteInformationsPostupak(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendPostupak(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.specifikacijes = this.refineData(dataFromBody);
  }

  protected refineData(data: ISpecifikacije[]): ISpecifikacije[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: ISpecifikacije[] | null): ISpecifikacije[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.specifikacijeService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }
  protected queryBackendPostupak(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = { 'sifraPostupka.in': this.postupak, sort: this.getSortQueryParam(predicate, ascending) };
    return this.specifikacijeService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  uploadFile(): any {
    const formData = new FormData();
    formData.append('files', this.fileInput.nativeElement.files[0]);

    this.specifikacijeService.UploadExcel(formData).subscribe((result: { toString: () => string | undefined }) => {
      this.message = result.toString();
      this.load();
    });
  }
  exportArray() {
    const onlyNameAndSymbolArr: Partial<ISpecifikacije>[] = this.specifikacijes.map(x => ({
      'sifra postupka': x.sifraPostupka,
      broj_partije: x.brojPartije,
      atc: x.atc,
      inn: x.inn,
      'farmaceutski oblik': x.farmaceutskiOblikLijeka,
      karakteristika: x.karakteristika,
      'jacina lijeka': x.jacinaLijeka,
      'trazena kolicina': x.trazenaKolicina,
      pakovanje: x.pakovanje,
      'jedinica mjere': x.jedinicaMjere,
      'procijenjena vrijednost': x.procijenjenaVrijednost,
      'jedinicna cijena': x.jedinicnaCijena,
    }));
    TableUtil.exportArrayToExcel(onlyNameAndSymbolArr, 'Specifikacija');
  }

  update(
    id?: number,
    sifraPostupka?: number,
    brojPartije?: number,
    atc?: string | null,
    inn?: string | null,
    farmaceutskiOblikLijeka?: string | null,
    karakteristika?: string | null,
    jacinaLijeka?: string | null,
    trazenaKolicina?: number | null,
    pakovanje?: string | null,
    jedinicaMjere?: string | null,
    procijenjenaVrijednost?: number,
    jedinicnaCijena?: number
  ): void {
    const modalRef = this.modalService.open(SpecifikacijeUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.sifraPostupka = sifraPostupka;
    modalRef.componentInstance.brojPartije = brojPartije;
    modalRef.componentInstance.atc = atc;
    modalRef.componentInstance.inn = inn;
    modalRef.componentInstance.farmaceutskiOblikLijeka = farmaceutskiOblikLijeka;
    modalRef.componentInstance.karakteristika = karakteristika;
    modalRef.componentInstance.jacinaLijeka = jacinaLijeka;
    modalRef.componentInstance.trazenaKolicina = trazenaKolicina;
    modalRef.componentInstance.pakovanje = pakovanje;
    modalRef.componentInstance.jedinicaMjere = jedinicaMjere;
    modalRef.componentInstance.procijenjenaVrijednost = procijenjenaVrijednost;
    modalRef.componentInstance.jedinicnaCijena = jedinicnaCijena;

    modalRef.closed.subscribe(() => {
      this.load();
    });
  }
  add(): void {
    const modalRef = this.modalService.open(SpecifikacijeUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.closed.subscribe(() => {
      this.load();
    });
  }
}
