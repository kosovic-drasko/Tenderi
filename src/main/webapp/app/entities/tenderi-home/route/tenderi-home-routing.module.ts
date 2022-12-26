import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TenderiHomeComponent } from '../list/tenderi-home.component';
import { ASC } from 'app/config/navigation.constants';

const tenderiHomeRoute: Routes = [
  {
    path: '',
    component: TenderiHomeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tenderiHomeRoute)],
  exports: [RouterModule],
})
export class TenderiHomeRoutingModule {}
