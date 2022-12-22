import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IViewVrednovanje, NewViewVrednovanje } from '../view-vrednovanje.model';

export type PartialUpdateViewVrednovanje = Partial<IViewVrednovanje> & Pick<IViewVrednovanje, 'id'>;

export type EntityResponseType = HttpResponse<IViewVrednovanje>;
export type EntityArrayResponseType = HttpResponse<IViewVrednovanje[]>;

@Injectable({ providedIn: 'root' })
export class ViewVrednovanjeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/view-vrednovanjes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IViewVrednovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViewVrednovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getViewVrednovanjeIdentifier(viewVrednovanje: Pick<IViewVrednovanje, 'id'>): number {
    return viewVrednovanje.id;
  }

  compareViewVrednovanje(o1: Pick<IViewVrednovanje, 'id'> | null, o2: Pick<IViewVrednovanje, 'id'> | null): boolean {
    return o1 && o2 ? this.getViewVrednovanjeIdentifier(o1) === this.getViewVrednovanjeIdentifier(o2) : o1 === o2;
  }

  addViewVrednovanjeToCollectionIfMissing<Type extends Pick<IViewVrednovanje, 'id'>>(
    viewVrednovanjeCollection: Type[],
    ...viewVrednovanjesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const viewVrednovanjes: Type[] = viewVrednovanjesToCheck.filter(isPresent);
    if (viewVrednovanjes.length > 0) {
      const viewVrednovanjeCollectionIdentifiers = viewVrednovanjeCollection.map(
        viewVrednovanjeItem => this.getViewVrednovanjeIdentifier(viewVrednovanjeItem)!
      );
      const viewVrednovanjesToAdd = viewVrednovanjes.filter(viewVrednovanjeItem => {
        const viewVrednovanjeIdentifier = this.getViewVrednovanjeIdentifier(viewVrednovanjeItem);
        if (viewVrednovanjeCollectionIdentifiers.includes(viewVrednovanjeIdentifier)) {
          return false;
        }
        viewVrednovanjeCollectionIdentifiers.push(viewVrednovanjeIdentifier);
        return true;
      });
      return [...viewVrednovanjesToAdd, ...viewVrednovanjeCollection];
    }
    return viewVrednovanjeCollection;
  }
}
