import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HvalePonudeComponent } from './list/hvale-ponude.component';
import { HvalePonudeDetailComponent } from './detail/hvale-ponude-detail.component';
import { HvalePonudeRoutingModule } from './route/hvale-ponude-routing.module';

@NgModule({
  imports: [SharedModule, HvalePonudeRoutingModule],
  declarations: [HvalePonudeComponent, HvalePonudeDetailComponent],
})
export class HvalePonudeModule {}
