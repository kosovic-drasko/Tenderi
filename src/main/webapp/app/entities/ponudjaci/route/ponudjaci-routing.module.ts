import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PonudjaciComponent } from '../list/ponudjaci.component';
import { PonudjaciDetailComponent } from '../detail/ponudjaci-detail.component';
import { PonudjaciUpdateComponent } from '../update/ponudjaci-update.component';
import { PonudjaciRoutingResolveService } from './ponudjaci-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ponudjaciRoute: Routes = [
  {
    path: '',
    component: PonudjaciComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PonudjaciDetailComponent,
    resolve: {
      ponudjaci: PonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PonudjaciUpdateComponent,
    resolve: {
      ponudjaci: PonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PonudjaciUpdateComponent,
    resolve: {
      ponudjaci: PonudjaciRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ponudjaciRoute)],
  exports: [RouterModule],
})
export class PonudjaciRoutingModule {}
