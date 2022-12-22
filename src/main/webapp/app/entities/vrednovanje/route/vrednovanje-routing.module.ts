import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VrednovanjeComponent } from '../list/vrednovanje.component';
import { VrednovanjeDetailComponent } from '../detail/vrednovanje-detail.component';
import { VrednovanjeRoutingResolveService } from './vrednovanje-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const vrednovanjeRoute: Routes = [
  {
    path: '',
    component: VrednovanjeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VrednovanjeDetailComponent,
    resolve: {
      vrednovanje: VrednovanjeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vrednovanjeRoute)],
  exports: [RouterModule],
})
export class VrednovanjeRoutingModule {}
