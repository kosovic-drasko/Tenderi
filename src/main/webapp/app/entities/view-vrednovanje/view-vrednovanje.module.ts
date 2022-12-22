import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ViewVrednovanjeComponent } from './list/view-vrednovanje.component';
import { ViewVrednovanjeDetailComponent } from './detail/view-vrednovanje-detail.component';
import { ViewVrednovanjeRoutingModule } from './route/view-vrednovanje-routing.module';

@NgModule({
  imports: [SharedModule, ViewVrednovanjeRoutingModule],
  declarations: [ViewVrednovanjeComponent, ViewVrednovanjeDetailComponent],
})
export class ViewVrednovanjeModule {}
