import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPonudjaci } from '../ponudjaci.model';
import { PonudjaciService } from '../service/ponudjaci.service';

@Injectable({ providedIn: 'root' })
export class PonudjaciRoutingResolveService implements Resolve<IPonudjaci | null> {
  constructor(protected service: PonudjaciService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPonudjaci | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ponudjaci: HttpResponse<IPonudjaci>) => {
          if (ponudjaci.body) {
            return of(ponudjaci.body);
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
