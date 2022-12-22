import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPonudjaci, NewPonudjaci } from '../ponudjaci.model';

export type PartialUpdatePonudjaci = Partial<IPonudjaci> & Pick<IPonudjaci, 'id'>;

export type EntityResponseType = HttpResponse<IPonudjaci>;
export type EntityArrayResponseType = HttpResponse<IPonudjaci[]>;

@Injectable({ providedIn: 'root' })
export class PonudjaciService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ponudjacis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ponudjaci: NewPonudjaci): Observable<EntityResponseType> {
    return this.http.post<IPonudjaci>(this.resourceUrl, ponudjaci, { observe: 'response' });
  }

  update(ponudjaci: IPonudjaci): Observable<EntityResponseType> {
    return this.http.put<IPonudjaci>(`${this.resourceUrl}/${this.getPonudjaciIdentifier(ponudjaci)}`, ponudjaci, { observe: 'response' });
  }

  partialUpdate(ponudjaci: PartialUpdatePonudjaci): Observable<EntityResponseType> {
    return this.http.patch<IPonudjaci>(`${this.resourceUrl}/${this.getPonudjaciIdentifier(ponudjaci)}`, ponudjaci, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPonudjaci>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPonudjaci[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPonudjaciIdentifier(ponudjaci: Pick<IPonudjaci, 'id'>): number {
    return ponudjaci.id;
  }

  comparePonudjaci(o1: Pick<IPonudjaci, 'id'> | null, o2: Pick<IPonudjaci, 'id'> | null): boolean {
    return o1 && o2 ? this.getPonudjaciIdentifier(o1) === this.getPonudjaciIdentifier(o2) : o1 === o2;
  }

  addPonudjaciToCollectionIfMissing<Type extends Pick<IPonudjaci, 'id'>>(
    ponudjaciCollection: Type[],
    ...ponudjacisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ponudjacis: Type[] = ponudjacisToCheck.filter(isPresent);
    if (ponudjacis.length > 0) {
      const ponudjaciCollectionIdentifiers = ponudjaciCollection.map(ponudjaciItem => this.getPonudjaciIdentifier(ponudjaciItem)!);
      const ponudjacisToAdd = ponudjacis.filter(ponudjaciItem => {
        const ponudjaciIdentifier = this.getPonudjaciIdentifier(ponudjaciItem);
        if (ponudjaciCollectionIdentifiers.includes(ponudjaciIdentifier)) {
          return false;
        }
        ponudjaciCollectionIdentifiers.push(ponudjaciIdentifier);
        return true;
      });
      return [...ponudjacisToAdd, ...ponudjaciCollection];
    }
    return ponudjaciCollection;
  }
}
