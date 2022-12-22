import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViewPrvorangiraniDetailComponent } from './view-prvorangirani-detail.component';

describe('ViewPrvorangirani Management Detail Component', () => {
  let comp: ViewPrvorangiraniDetailComponent;
  let fixture: ComponentFixture<ViewPrvorangiraniDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewPrvorangiraniDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ viewPrvorangirani: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ViewPrvorangiraniDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ViewPrvorangiraniDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load viewPrvorangirani on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.viewPrvorangirani).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
