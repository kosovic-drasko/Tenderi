import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHvalePonude, NewHvalePonude } from '../hvale-ponude.model';

export type PartialUpdateHvalePonude = Partial<IHvalePonude> & Pick<IHvalePonude, 'id'>;

export type EntityResponseType = HttpResponse<IHvalePonude>;
export type EntityArrayResponseType = HttpResponse<IHvalePonude[]>;

@Injectable({ providedIn: 'root' })
export class HvalePonudeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hvale-ponudes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHvalePonude>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHvalePonude[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getHvalePonudeIdentifier(hvalePonude: Pick<IHvalePonude, 'id'>): number {
    return hvalePonude.id;
  }

  compareHvalePonude(o1: Pick<IHvalePonude, 'id'> | null, o2: Pick<IHvalePonude, 'id'> | null): boolean {
    return o1 && o2 ? this.getHvalePonudeIdentifier(o1) === this.getHvalePonudeIdentifier(o2) : o1 === o2;
  }

  addHvalePonudeToCollectionIfMissing<Type extends Pick<IHvalePonude, 'id'>>(
    hvalePonudeCollection: Type[],
    ...hvalePonudesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hvalePonudes: Type[] = hvalePonudesToCheck.filter(isPresent);
    if (hvalePonudes.length > 0) {
      const hvalePonudeCollectionIdentifiers = hvalePonudeCollection.map(
        hvalePonudeItem => this.getHvalePonudeIdentifier(hvalePonudeItem)!
      );
      const hvalePonudesToAdd = hvalePonudes.filter(hvalePonudeItem => {
        const hvalePonudeIdentifier = this.getHvalePonudeIdentifier(hvalePonudeItem);
        if (hvalePonudeCollectionIdentifiers.includes(hvalePonudeIdentifier)) {
          return false;
        }
        hvalePonudeCollectionIdentifiers.push(hvalePonudeIdentifier);
        return true;
      });
      return [...hvalePonudesToAdd, ...hvalePonudeCollection];
    }
    return hvalePonudeCollection;
  }
}
