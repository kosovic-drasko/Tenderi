import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VrednovanjeComponent } from './list/vrednovanje.component';
import { VrednovanjeDetailComponent } from './detail/vrednovanje-detail.component';
import { VrednovanjeRoutingModule } from './route/vrednovanje-routing.module';

@NgModule({
  imports: [SharedModule, VrednovanjeRoutingModule],
  declarations: [VrednovanjeComponent, VrednovanjeDetailComponent],
})
export class VrednovanjeModule {}
