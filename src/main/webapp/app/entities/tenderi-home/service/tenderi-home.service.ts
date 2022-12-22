import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITenderiHome, NewTenderiHome } from '../tenderi-home.model';

export type PartialUpdateTenderiHome = Partial<ITenderiHome> & Pick<ITenderiHome, 'id'>;

export type EntityResponseType = HttpResponse<ITenderiHome>;
export type EntityArrayResponseType = HttpResponse<ITenderiHome[]>;

@Injectable({ providedIn: 'root' })
export class TenderiHomeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tenderi-homes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITenderiHome>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITenderiHome[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getTenderiHomeIdentifier(tenderiHome: Pick<ITenderiHome, 'id'>): number {
    return tenderiHome.id;
  }

  compareTenderiHome(o1: Pick<ITenderiHome, 'id'> | null, o2: Pick<ITenderiHome, 'id'> | null): boolean {
    return o1 && o2 ? this.getTenderiHomeIdentifier(o1) === this.getTenderiHomeIdentifier(o2) : o1 === o2;
  }

  addTenderiHomeToCollectionIfMissing<Type extends Pick<ITenderiHome, 'id'>>(
    tenderiHomeCollection: Type[],
    ...tenderiHomesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tenderiHomes: Type[] = tenderiHomesToCheck.filter(isPresent);
    if (tenderiHomes.length > 0) {
      const tenderiHomeCollectionIdentifiers = tenderiHomeCollection.map(
        tenderiHomeItem => this.getTenderiHomeIdentifier(tenderiHomeItem)!
      );
      const tenderiHomesToAdd = tenderiHomes.filter(tenderiHomeItem => {
        const tenderiHomeIdentifier = this.getTenderiHomeIdentifier(tenderiHomeItem);
        if (tenderiHomeCollectionIdentifiers.includes(tenderiHomeIdentifier)) {
          return false;
        }
        tenderiHomeCollectionIdentifiers.push(tenderiHomeIdentifier);
        return true;
      });
      return [...tenderiHomesToAdd, ...tenderiHomeCollection];
    }
    return tenderiHomeCollection;
  }
}
