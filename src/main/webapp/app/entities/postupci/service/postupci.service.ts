import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPostupci, NewPostupci } from '../postupci.model';

export type PartialUpdatePostupci = Partial<IPostupci> & Pick<IPostupci, 'id'>;

type RestOf<T extends IPostupci | NewPostupci> = Omit<T, 'datumObjave' | 'datumOtvaranja'> & {
  datumObjave?: string | null;
  datumOtvaranja?: string | null;
};

export type RestPostupci = RestOf<IPostupci>;

export type NewRestPostupci = RestOf<NewPostupci>;

export type PartialUpdateRestPostupci = RestOf<PartialUpdatePostupci>;

export type EntityResponseType = HttpResponse<IPostupci>;
export type EntityArrayResponseType = HttpResponse<IPostupci[]>;

@Injectable({ providedIn: 'root' })
export class PostupciService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/postupcis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(postupci: NewPostupci): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(postupci);
    return this.http
      .post<RestPostupci>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(postupci: IPostupci): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(postupci);
    return this.http
      .put<RestPostupci>(`${this.resourceUrl}/${this.getPostupciIdentifier(postupci)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(postupci: PartialUpdatePostupci): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(postupci);
    return this.http
      .patch<RestPostupci>(`${this.resourceUrl}/${this.getPostupciIdentifier(postupci)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPostupci>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPostupci[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPostupciIdentifier(postupci: Pick<IPostupci, 'id'>): number {
    return postupci.id;
  }

  comparePostupci(o1: Pick<IPostupci, 'id'> | null, o2: Pick<IPostupci, 'id'> | null): boolean {
    return o1 && o2 ? this.getPostupciIdentifier(o1) === this.getPostupciIdentifier(o2) : o1 === o2;
  }

  addPostupciToCollectionIfMissing<Type extends Pick<IPostupci, 'id'>>(
    postupciCollection: Type[],
    ...postupcisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const postupcis: Type[] = postupcisToCheck.filter(isPresent);
    if (postupcis.length > 0) {
      const postupciCollectionIdentifiers = postupciCollection.map(postupciItem => this.getPostupciIdentifier(postupciItem)!);
      const postupcisToAdd = postupcis.filter(postupciItem => {
        const postupciIdentifier = this.getPostupciIdentifier(postupciItem);
        if (postupciCollectionIdentifiers.includes(postupciIdentifier)) {
          return false;
        }
        postupciCollectionIdentifiers.push(postupciIdentifier);
        return true;
      });
      return [...postupcisToAdd, ...postupciCollection];
    }
    return postupciCollection;
  }

  protected convertDateFromClient<T extends IPostupci | NewPostupci | PartialUpdatePostupci>(postupci: T): RestOf<T> {
    return {
      ...postupci,
      datumObjave: postupci.datumObjave?.format(DATE_FORMAT) ?? null,
      datumOtvaranja: postupci.datumOtvaranja?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPostupci: RestPostupci): IPostupci {
    return {
      ...restPostupci,
      datumObjave: restPostupci.datumObjave ? dayjs(restPostupci.datumObjave) : undefined,
      datumOtvaranja: restPostupci.datumOtvaranja ? dayjs(restPostupci.datumOtvaranja) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPostupci>): HttpResponse<IPostupci> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPostupci[]>): HttpResponse<IPostupci[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
