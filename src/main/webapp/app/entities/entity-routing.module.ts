import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ponude',
        data: { pageTitle: 'tenderiApp.ponude.home.title' },
        loadChildren: () => import('./ponude/ponude.module').then(m => m.PonudeModule),
      },
      {
        path: 'hvale-ponude',
        data: { pageTitle: 'tenderiApp.hvalePonude.home.title' },
        loadChildren: () => import('./hvale-ponude/hvale-ponude.module').then(m => m.HvalePonudeModule),
      },
      {
        path: 'ponudjaci',
        data: { pageTitle: 'tenderiApp.ponudjaci.home.title' },
        loadChildren: () => import('./ponudjaci/ponudjaci.module').then(m => m.PonudjaciModule),
      },
      {
        path: 'postupci',
        data: { pageTitle: 'tenderiApp.postupci.home.title' },
        loadChildren: () => import('./postupci/postupci.module').then(m => m.PostupciModule),
      },
      {
        path: 'view-ponude',
        data: { pageTitle: 'tenderiApp.viewPonude.home.title' },
        loadChildren: () => import('./view-ponude/view-ponude.module').then(m => m.ViewPonudeModule),
      },
      {
        path: 'specifikacije',
        data: { pageTitle: 'tenderiApp.specifikacije.home.title' },
        loadChildren: () => import('./specifikacije/specifikacije.module').then(m => m.SpecifikacijeModule),
      },
      {
        path: 'tenderi-home',
        data: { pageTitle: 'tenderiApp.tenderiHome.home.title' },
        loadChildren: () => import('./tenderi-home/tenderi-home.module').then(m => m.TenderiHomeModule),
      },
      {
        path: 'vrednovanje',
        data: { pageTitle: 'tenderiApp.vrednovanje.home.title' },
        loadChildren: () => import('./vrednovanje/vrednovanje.module').then(m => m.VrednovanjeModule),
      },
      {
        path: 'prvorangirani',
        data: { pageTitle: 'tenderiApp.prvorangirani.home.title' },
        loadChildren: () => import('./prvorangirani/prvorangirani.module').then(m => m.PrvorangiraniModule),
      },
      {
        path: 'view-prvorangirani',
        data: { pageTitle: 'tenderiApp.viewPrvorangirani.home.title' },
        loadChildren: () => import('./view-prvorangirani/view-prvorangirani.module').then(m => m.ViewPrvorangiraniModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
