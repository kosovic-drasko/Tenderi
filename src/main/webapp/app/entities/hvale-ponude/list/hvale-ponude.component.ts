import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Observable, switchMap, tap } from 'rxjs';

import { IHvalePonude } from '../hvale-ponude.model';
import { ASC, DESC, SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, HvalePonudeService } from '../service/hvale-ponude.service';
import { SortService } from 'app/shared/sort/sort.service';

@Component({
  selector: 'jhi-hvale-ponude',
  templateUrl: './hvale-ponude.component.html',
})
export class HvalePonudeComponent implements OnInit {
  hvalePonudes?: IHvalePonude[];
  isLoading = false;

  predicate = 'id';
  ascending = true;
  ukupno_procjenjeno?: number;
  @Input() postupak: any;
  constructor(
    protected hvalePonudeService: HvalePonudeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService
  ) {}

  trackId = (_index: number, item: IHvalePonude): number => this.hvalePonudeService.getHvalePonudeIdentifier(item);

  ngOnInit(): void {
    if (this.postupak !== undefined) {
      this.loadSifraPostupka();
    } else {
      this.load();
      console.log('Postupak je >>>>>>>>', this.postupak);
    }
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.ukupno_procjenjeno = res.body?.reduce((acc, specifikacije) => acc + specifikacije.procijenjenaVrijednost!, 0);
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
    this.hvalePonudes = this.refineData(dataFromBody);
  }

  protected refineData(data: IHvalePonude[]): IHvalePonude[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IHvalePonude[] | null): IHvalePonude[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.hvalePonudeService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
  loadSifraPostupka(): void {
    this.loadFromBackendWithRouteInformationsPostupak().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        this.ukupno_procjenjeno = res.body?.reduce((acc, specifikacije) => acc + specifikacije.procijenjenaVrijednost!, 0);
      },
    });
  }
  protected loadFromBackendWithRouteInformationsPostupak(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackendPostupak(this.predicate, this.ascending))
    );
  }
  protected queryBackendPostupak(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = { 'sifraPostupka.in': this.postupak, sort: this.getSortQueryParam(predicate, ascending) };
    return this.hvalePonudeService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }
}
