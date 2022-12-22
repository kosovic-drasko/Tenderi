import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVrednovanje, NewVrednovanje } from '../vrednovanje.model';

export type PartialUpdateVrednovanje = Partial<IVrednovanje> & Pick<IVrednovanje, 'id'>;

export type EntityResponseType = HttpResponse<IVrednovanje>;
export type EntityArrayResponseType = HttpResponse<IVrednovanje[]>;

@Injectable({ providedIn: 'root' })
export class VrednovanjeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vrednovanjes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVrednovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVrednovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getVrednovanjeIdentifier(vrednovanje: Pick<IVrednovanje, 'id'>): number {
    return vrednovanje.id;
  }

  compareVrednovanje(o1: Pick<IVrednovanje, 'id'> | null, o2: Pick<IVrednovanje, 'id'> | null): boolean {
    return o1 && o2 ? this.getVrednovanjeIdentifier(o1) === this.getVrednovanjeIdentifier(o2) : o1 === o2;
  }

  addVrednovanjeToCollectionIfMissing<Type extends Pick<IVrednovanje, 'id'>>(
    vrednovanjeCollection: Type[],
    ...vrednovanjesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vrednovanjes: Type[] = vrednovanjesToCheck.filter(isPresent);
    if (vrednovanjes.length > 0) {
      const vrednovanjeCollectionIdentifiers = vrednovanjeCollection.map(
        vrednovanjeItem => this.getVrednovanjeIdentifier(vrednovanjeItem)!
      );
      const vrednovanjesToAdd = vrednovanjes.filter(vrednovanjeItem => {
        const vrednovanjeIdentifier = this.getVrednovanjeIdentifier(vrednovanjeItem);
        if (vrednovanjeCollectionIdentifiers.includes(vrednovanjeIdentifier)) {
          return false;
        }
        vrednovanjeCollectionIdentifiers.push(vrednovanjeIdentifier);
        return true;
      });
      return [...vrednovanjesToAdd, ...vrednovanjeCollection];
    }
    return vrednovanjeCollection;
  }
}
