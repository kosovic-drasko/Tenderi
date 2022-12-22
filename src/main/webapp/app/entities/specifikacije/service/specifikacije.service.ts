import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpecifikacije, NewSpecifikacije } from '../specifikacije.model';

export type PartialUpdateSpecifikacije = Partial<ISpecifikacije> & Pick<ISpecifikacije, 'id'>;

export type EntityResponseType = HttpResponse<ISpecifikacije>;
export type EntityArrayResponseType = HttpResponse<ISpecifikacije[]>;

@Injectable({ providedIn: 'root' })
export class SpecifikacijeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specifikacijes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(specifikacije: NewSpecifikacije): Observable<EntityResponseType> {
    return this.http.post<ISpecifikacije>(this.resourceUrl, specifikacije, { observe: 'response' });
  }

  update(specifikacije: ISpecifikacije): Observable<EntityResponseType> {
    return this.http.put<ISpecifikacije>(`${this.resourceUrl}/${this.getSpecifikacijeIdentifier(specifikacije)}`, specifikacije, {
      observe: 'response',
    });
  }

  partialUpdate(specifikacije: PartialUpdateSpecifikacije): Observable<EntityResponseType> {
    return this.http.patch<ISpecifikacije>(`${this.resourceUrl}/${this.getSpecifikacijeIdentifier(specifikacije)}`, specifikacije, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecifikacije>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecifikacije[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSpecifikacijeIdentifier(specifikacije: Pick<ISpecifikacije, 'id'>): number {
    return specifikacije.id;
  }

  compareSpecifikacije(o1: Pick<ISpecifikacije, 'id'> | null, o2: Pick<ISpecifikacije, 'id'> | null): boolean {
    return o1 && o2 ? this.getSpecifikacijeIdentifier(o1) === this.getSpecifikacijeIdentifier(o2) : o1 === o2;
  }

  addSpecifikacijeToCollectionIfMissing<Type extends Pick<ISpecifikacije, 'id'>>(
    specifikacijeCollection: Type[],
    ...specifikacijesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const specifikacijes: Type[] = specifikacijesToCheck.filter(isPresent);
    if (specifikacijes.length > 0) {
      const specifikacijeCollectionIdentifiers = specifikacijeCollection.map(
        specifikacijeItem => this.getSpecifikacijeIdentifier(specifikacijeItem)!
      );
      const specifikacijesToAdd = specifikacijes.filter(specifikacijeItem => {
        const specifikacijeIdentifier = this.getSpecifikacijeIdentifier(specifikacijeItem);
        if (specifikacijeCollectionIdentifiers.includes(specifikacijeIdentifier)) {
          return false;
        }
        specifikacijeCollectionIdentifiers.push(specifikacijeIdentifier);
        return true;
      });
      return [...specifikacijesToAdd, ...specifikacijeCollection];
    }
    return specifikacijeCollection;
  }
}
