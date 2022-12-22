import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HvalePonudeDetailComponent } from './hvale-ponude-detail.component';

describe('HvalePonude Management Detail Component', () => {
  let comp: HvalePonudeDetailComponent;
  let fixture: ComponentFixture<HvalePonudeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HvalePonudeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hvalePonude: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HvalePonudeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HvalePonudeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hvalePonude on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hvalePonude).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
