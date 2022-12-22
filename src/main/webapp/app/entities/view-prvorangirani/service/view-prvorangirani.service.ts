import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IViewPrvorangirani, NewViewPrvorangirani } from '../view-prvorangirani.model';

export type PartialUpdateViewPrvorangirani = Partial<IViewPrvorangirani> & Pick<IViewPrvorangirani, 'id'>;

export type EntityResponseType = HttpResponse<IViewPrvorangirani>;
export type EntityArrayResponseType = HttpResponse<IViewPrvorangirani[]>;

@Injectable({ providedIn: 'root' })
export class ViewPrvorangiraniService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/view-prvorangiranis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IViewPrvorangirani>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViewPrvorangirani[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getViewPrvorangiraniIdentifier(viewPrvorangirani: Pick<IViewPrvorangirani, 'id'>): number {
    return viewPrvorangirani.id;
  }

  compareViewPrvorangirani(o1: Pick<IViewPrvorangirani, 'id'> | null, o2: Pick<IViewPrvorangirani, 'id'> | null): boolean {
    return o1 && o2 ? this.getViewPrvorangiraniIdentifier(o1) === this.getViewPrvorangiraniIdentifier(o2) : o1 === o2;
  }

  addViewPrvorangiraniToCollectionIfMissing<Type extends Pick<IViewPrvorangirani, 'id'>>(
    viewPrvorangiraniCollection: Type[],
    ...viewPrvorangiranisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const viewPrvorangiranis: Type[] = viewPrvorangiranisToCheck.filter(isPresent);
    if (viewPrvorangiranis.length > 0) {
      const viewPrvorangiraniCollectionIdentifiers = viewPrvorangiraniCollection.map(
        viewPrvorangiraniItem => this.getViewPrvorangiraniIdentifier(viewPrvorangiraniItem)!
      );
      const viewPrvorangiranisToAdd = viewPrvorangiranis.filter(viewPrvorangiraniItem => {
        const viewPrvorangiraniIdentifier = this.getViewPrvorangiraniIdentifier(viewPrvorangiraniItem);
        if (viewPrvorangiraniCollectionIdentifiers.includes(viewPrvorangiraniIdentifier)) {
          return false;
        }
        viewPrvorangiraniCollectionIdentifiers.push(viewPrvorangiraniIdentifier);
        return true;
      });
      return [...viewPrvorangiranisToAdd, ...viewPrvorangiraniCollection];
    }
    return viewPrvorangiraniCollection;
  }
}
