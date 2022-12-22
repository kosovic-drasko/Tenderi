import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VrednovanjeDetailComponent } from './vrednovanje-detail.component';

describe('Vrednovanje Management Detail Component', () => {
  let comp: VrednovanjeDetailComponent;
  let fixture: ComponentFixture<VrednovanjeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VrednovanjeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vrednovanje: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VrednovanjeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VrednovanjeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vrednovanje on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vrednovanje).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
