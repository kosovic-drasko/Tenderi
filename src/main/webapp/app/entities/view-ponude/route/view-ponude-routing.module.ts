import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ViewPonudeComponent } from '../list/view-ponude.component';
import { ViewPonudeDetailComponent } from '../detail/view-ponude-detail.component';
import { ViewPonudeUpdateComponent } from '../update/view-ponude-update.component';
import { ViewPonudeRoutingResolveService } from './view-ponude-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const viewPonudeRoute: Routes = [
  {
    path: '',
    component: ViewPonudeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ViewPonudeDetailComponent,
    resolve: {
      viewPonude: ViewPonudeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ViewPonudeUpdateComponent,
    resolve: {
      viewPonude: ViewPonudeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ViewPonudeUpdateComponent,
    resolve: {
      viewPonude: ViewPonudeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(viewPonudeRoute)],
  exports: [RouterModule],
})
export class ViewPonudeRoutingModule {}
