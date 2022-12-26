import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TenderiHomeComponent } from './list/tenderi-home.component';
import { TenderiHomeRoutingModule } from './route/tenderi-home-routing.module';
import { SpecifikacijeModule } from '../specifikacije/specifikacije.module';
import { VrednovanjeModule } from '../vrednovanje/vrednovanje.module';
import { PrvorangiraniModule } from '../prvorangirani/prvorangirani.module';
import { HvalePonudeModule } from '../hvale-ponude/hvale-ponude.module';
import { ViewPonudeModule } from '../view-ponude/view-ponude.module';

@NgModule({
  imports: [
    SharedModule,
    TenderiHomeRoutingModule,
    SpecifikacijeModule,
    VrednovanjeModule,
    PrvorangiraniModule,
    HvalePonudeModule,
    ViewPonudeModule,
  ],
  declarations: [TenderiHomeComponent],
})
export class TenderiHomeModule {}
