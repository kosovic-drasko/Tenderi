import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IViewPonude, NewViewPonude } from '../view-ponude.model';

export type PartialUpdateViewPonude = Partial<IViewPonude> & Pick<IViewPonude, 'id'>;

export type EntityResponseType = HttpResponse<IViewPonude>;
export type EntityArrayResponseType = HttpResponse<IViewPonude[]>;

@Injectable({ providedIn: 'root' })
export class ViewPonudeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/view-ponudes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(viewPonude: NewViewPonude): Observable<EntityResponseType> {
    return this.http.post<IViewPonude>(this.resourceUrl, viewPonude, { observe: 'response' });
  }

  update(viewPonude: IViewPonude): Observable<EntityResponseType> {
    return this.http.put<IViewPonude>(`${this.resourceUrl}/${this.getViewPonudeIdentifier(viewPonude)}`, viewPonude, {
      observe: 'response',
    });
  }

  partialUpdate(viewPonude: PartialUpdateViewPonude): Observable<EntityResponseType> {
    return this.http.patch<IViewPonude>(`${this.resourceUrl}/${this.getViewPonudeIdentifier(viewPonude)}`, viewPonude, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IViewPonude>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViewPonude[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getViewPonudeIdentifier(viewPonude: Pick<IViewPonude, 'id'>): number {
    return viewPonude.id;
  }

  compareViewPonude(o1: Pick<IViewPonude, 'id'> | null, o2: Pick<IViewPonude, 'id'> | null): boolean {
    return o1 && o2 ? this.getViewPonudeIdentifier(o1) === this.getViewPonudeIdentifier(o2) : o1 === o2;
  }

  addViewPonudeToCollectionIfMissing<Type extends Pick<IViewPonude, 'id'>>(
    viewPonudeCollection: Type[],
    ...viewPonudesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const viewPonudes: Type[] = viewPonudesToCheck.filter(isPresent);
    if (viewPonudes.length > 0) {
      const viewPonudeCollectionIdentifiers = viewPonudeCollection.map(viewPonudeItem => this.getViewPonudeIdentifier(viewPonudeItem)!);
      const viewPonudesToAdd = viewPonudes.filter(viewPonudeItem => {
        const viewPonudeIdentifier = this.getViewPonudeIdentifier(viewPonudeItem);
        if (viewPonudeCollectionIdentifiers.includes(viewPonudeIdentifier)) {
          return false;
        }
        viewPonudeCollectionIdentifiers.push(viewPonudeIdentifier);
        return true;
      });
      return [...viewPonudesToAdd, ...viewPonudeCollection];
    }
    return viewPonudeCollection;
  }
}
