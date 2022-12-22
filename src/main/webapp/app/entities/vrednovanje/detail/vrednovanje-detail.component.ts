import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVrednovanje } from '../vrednovanje.model';

@Component({
  selector: 'jhi-vrednovanje-detail',
  templateUrl: './vrednovanje-detail.component.html',
})
export class VrednovanjeDetailComponent implements OnInit {
  vrednovanje: IVrednovanje | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vrednovanje }) => {
      this.vrednovanje = vrednovanje;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
