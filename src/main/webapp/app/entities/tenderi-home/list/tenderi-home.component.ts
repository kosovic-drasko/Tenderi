import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
@Component({
  selector: 'jhi-tenderi-home',
  templateUrl: './tenderi-home.component.html',
  styleUrls: ['./tenderi-home.components.scss'],
})
export class TenderiHomeComponent implements OnInit {
  isLoading = false;
  sifra?: any;
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.sifra = params['sifra'];
    });
  }
}
