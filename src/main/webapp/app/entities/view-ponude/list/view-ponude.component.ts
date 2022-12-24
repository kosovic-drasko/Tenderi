import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IViewPonude } from '../view-ponude.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, ViewPonudeService } from '../service/view-ponude.service';
import { ViewPonudeDeleteDialogComponent } from '../delete/view-ponude-delete-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';
import { PonudeService } from '../../ponude/service/ponude.service';
import { PonudeDeleteDialogComponent } from '../../ponude/delete/ponude-delete-dialog.component';
import { IPonude } from '../../ponude/ponude.model';
import { PonudeUpdateComponent } from '../../ponude/update/ponude-update.component';

@Component({
  selector: 'jhi-view-ponude',
  templateUrl: './view-ponude.component.html',
})
export class ViewPonudeComponent implements OnInit {
  viewPonudes?: IViewPonude[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  constructor(
    protected viewPonudeService: ViewPonudeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal,
    protected ponudeService: PonudeService
  ) {}

  trackId = (_index: number, item: IViewPonude): number => this.viewPonudeService.getViewPonudeIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  delete(ponude: IPonude): void {
    const modalRef = this.modalService.open(PonudeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ponude = ponude;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.load();
      }
    });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
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

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.viewPonudes = this.refineData(dataFromBody);
  }

  protected refineData(data: IViewPonude[]): IViewPonude[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IViewPonude[] | null): IViewPonude[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.viewPonudeService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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

  update(
    id?: number,
    sifraPostupka?: number,
    sifraPonude?: number,
    brojPartije?: number,
    sifraPonudjaca?: number | null,
    nazivProizvodjaca?: string | null,
    zasticeniNaziv?: string | null,
    karakteristika?: string | null,
    ponudjenaVrijednost?: number,
    jedinicnaCijena?: number | null,
    selected?: boolean | null,
    rokIsporuke?: number
  ): void {
    const modalRef = this.modalService.open(PonudeUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.sifraPostupka = sifraPostupka;
    modalRef.componentInstance.sifraPonude = sifraPonude;
    modalRef.componentInstance.brojPartije = brojPartije;
    modalRef.componentInstance.sifraPonudjaca = sifraPonudjaca;
    modalRef.componentInstance.nazivProizvodjaca = nazivProizvodjaca;
    modalRef.componentInstance.zasticeniNaziv = zasticeniNaziv;
    modalRef.componentInstance.karakteristika = karakteristika;
    modalRef.componentInstance.ponudjenaVrijednost = ponudjenaVrijednost;
    modalRef.componentInstance.jedinicnaCijena = jedinicnaCijena;
    modalRef.componentInstance.selected = selected;
    modalRef.componentInstance.rokIsporuke = rokIsporuke;

    modalRef.closed.subscribe(() => {
      // if (this.postupak !== undefined) {
      //   this.loadPageSifraPostupka();
      // } else {
      this.load();
      // }
    });
  }
  add(): void {
    const modalRef = this.modalService.open(PonudeUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.closed.subscribe(() => {
      this.load();
    });
  }
}
