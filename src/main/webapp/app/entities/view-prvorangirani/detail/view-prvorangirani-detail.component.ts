import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IViewPrvorangirani } from '../view-prvorangirani.model';

@Component({
  selector: 'jhi-view-prvorangirani-detail',
  templateUrl: './view-prvorangirani-detail.component.html',
})
export class ViewPrvorangiraniDetailComponent implements OnInit {
  viewPrvorangirani: IViewPrvorangirani | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viewPrvorangirani }) => {
      this.viewPrvorangirani = viewPrvorangirani;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
