import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViewVrednovanjeDetailComponent } from './view-vrednovanje-detail.component';

describe('ViewVrednovanje Management Detail Component', () => {
  let comp: ViewVrednovanjeDetailComponent;
  let fixture: ComponentFixture<ViewVrednovanjeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewVrednovanjeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ viewVrednovanje: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ViewVrednovanjeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ViewVrednovanjeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load viewVrednovanje on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.viewVrednovanje).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
