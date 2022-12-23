import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPonude, NewPonude } from '../ponude.model';

export type PartialUpdatePonude = Partial<IPonude> & Pick<IPonude, 'id'>;

export type EntityResponseType = HttpResponse<IPonude>;
export type EntityArrayResponseType = HttpResponse<IPonude[]>;

@Injectable({ providedIn: 'root' })
export class PonudeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ponudes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ponude: NewPonude): Observable<EntityResponseType> {
    return this.http.post<IPonude>(this.resourceUrl, ponude, { observe: 'response' });
  }

  update(ponude: IPonude): Observable<EntityResponseType> {
    return this.http.put<IPonude>(`${this.resourceUrl}/${this.getPonudeIdentifier(ponude)}`, ponude, { observe: 'response' });
  }

  partialUpdate(ponude: PartialUpdatePonude): Observable<EntityResponseType> {
    return this.http.patch<IPonude>(`${this.resourceUrl}/${this.getPonudeIdentifier(ponude)}`, ponude, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPonude>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPonude[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPonudeIdentifier(ponude: Pick<IPonude, 'id'>): number {
    return ponude.id;
  }

  comparePonude(o1: Pick<IPonude, 'id'> | null, o2: Pick<IPonude, 'id'> | null): boolean {
    return o1 && o2 ? this.getPonudeIdentifier(o1) === this.getPonudeIdentifier(o2) : o1 === o2;
  }

  addPonudeToCollectionIfMissing<Type extends Pick<IPonude, 'id'>>(
    ponudeCollection: Type[],
    ...ponudesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ponudes: Type[] = ponudesToCheck.filter(isPresent);
    if (ponudes.length > 0) {
      const ponudeCollectionIdentifiers = ponudeCollection.map(ponudeItem => this.getPonudeIdentifier(ponudeItem)!);
      const ponudesToAdd = ponudes.filter(ponudeItem => {
        const ponudeIdentifier = this.getPonudeIdentifier(ponudeItem);
        if (ponudeCollectionIdentifiers.includes(ponudeIdentifier)) {
          return false;
        }
        ponudeCollectionIdentifiers.push(ponudeIdentifier);
        return true;
      });
      return [...ponudesToAdd, ...ponudeCollection];
    }
    return ponudeCollection;
  }
}
