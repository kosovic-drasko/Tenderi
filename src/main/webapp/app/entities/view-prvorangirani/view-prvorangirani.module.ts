import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ViewPrvorangiraniComponent } from './list/view-prvorangirani.component';
import { ViewPrvorangiraniDetailComponent } from './detail/view-prvorangirani-detail.component';
import { ViewPrvorangiraniRoutingModule } from './route/view-prvorangirani-routing.module';

@NgModule({
  imports: [SharedModule, ViewPrvorangiraniRoutingModule],
  declarations: [ViewPrvorangiraniComponent, ViewPrvorangiraniDetailComponent],
})
export class ViewPrvorangiraniModule {}
