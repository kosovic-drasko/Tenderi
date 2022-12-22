import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrvorangiraniComponent } from './list/prvorangirani.component';
import { PrvorangiraniDetailComponent } from './detail/prvorangirani-detail.component';
import { PrvorangiraniRoutingModule } from './route/prvorangirani-routing.module';

@NgModule({
  imports: [SharedModule, PrvorangiraniRoutingModule],
  declarations: [PrvorangiraniComponent, PrvorangiraniDetailComponent],
})
export class PrvorangiraniModule {}
