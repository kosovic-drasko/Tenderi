import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVrednovanje } from '../vrednovanje.model';
import { VrednovanjeService } from '../service/vrednovanje.service';

@Injectable({ providedIn: 'root' })
export class VrednovanjeRoutingResolveService implements Resolve<IVrednovanje | null> {
  constructor(protected service: VrednovanjeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVrednovanje | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vrednovanje: HttpResponse<IVrednovanje>) => {
          if (vrednovanje.body) {
            return of(vrednovanje.body);
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
