import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PonudjaciComponent } from './list/ponudjaci.component';
import { PonudjaciDetailComponent } from './detail/ponudjaci-detail.component';
import { PonudjaciUpdateComponent } from './update/ponudjaci-update.component';
import { PonudjaciDeleteDialogComponent } from './delete/ponudjaci-delete-dialog.component';
import { PonudjaciRoutingModule } from './route/ponudjaci-routing.module';

@NgModule({
  imports: [SharedModule, PonudjaciRoutingModule],
  declarations: [PonudjaciComponent, PonudjaciDetailComponent, PonudjaciUpdateComponent, PonudjaciDeleteDialogComponent],
})
export class PonudjaciModule {}
