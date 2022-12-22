import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrvorangirani, NewPrvorangirani } from '../prvorangirani.model';

export type PartialUpdatePrvorangirani = Partial<IPrvorangirani> & Pick<IPrvorangirani, 'id'>;

export type EntityResponseType = HttpResponse<IPrvorangirani>;
export type EntityArrayResponseType = HttpResponse<IPrvorangirani[]>;

@Injectable({ providedIn: 'root' })
export class PrvorangiraniService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prvorangiranis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrvorangirani>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrvorangirani[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getPrvorangiraniIdentifier(prvorangirani: Pick<IPrvorangirani, 'id'>): number {
    return prvorangirani.id;
  }

  comparePrvorangirani(o1: Pick<IPrvorangirani, 'id'> | null, o2: Pick<IPrvorangirani, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrvorangiraniIdentifier(o1) === this.getPrvorangiraniIdentifier(o2) : o1 === o2;
  }

  addPrvorangiraniToCollectionIfMissing<Type extends Pick<IPrvorangirani, 'id'>>(
    prvorangiraniCollection: Type[],
    ...prvorangiranisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prvorangiranis: Type[] = prvorangiranisToCheck.filter(isPresent);
    if (prvorangiranis.length > 0) {
      const prvorangiraniCollectionIdentifiers = prvorangiraniCollection.map(
        prvorangiraniItem => this.getPrvorangiraniIdentifier(prvorangiraniItem)!
      );
      const prvorangiranisToAdd = prvorangiranis.filter(prvorangiraniItem => {
        const prvorangiraniIdentifier = this.getPrvorangiraniIdentifier(prvorangiraniItem);
        if (prvorangiraniCollectionIdentifiers.includes(prvorangiraniIdentifier)) {
          return false;
        }
        prvorangiraniCollectionIdentifiers.push(prvorangiraniIdentifier);
        return true;
      });
      return [...prvorangiranisToAdd, ...prvorangiraniCollection];
    }
    return prvorangiraniCollection;
  }
}
