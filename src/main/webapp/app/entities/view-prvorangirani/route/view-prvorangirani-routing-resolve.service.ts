import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IViewPrvorangirani } from '../view-prvorangirani.model';
import { ViewPrvorangiraniService } from '../service/view-prvorangirani.service';

@Injectable({ providedIn: 'root' })
export class ViewPrvorangiraniRoutingResolveService implements Resolve<IViewPrvorangirani | null> {
  constructor(protected service: ViewPrvorangiraniService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IViewPrvorangirani | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((viewPrvorangirani: HttpResponse<IViewPrvorangirani>) => {
          if (viewPrvorangirani.body) {
            return of(viewPrvorangirani.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
