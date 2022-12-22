import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrvorangiraniDetailComponent } from './prvorangirani-detail.component';

describe('Prvorangirani Management Detail Component', () => {
  let comp: PrvorangiraniDetailComponent;
  let fixture: ComponentFixture<PrvorangiraniDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrvorangiraniDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ prvorangirani: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrvorangiraniDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrvorangiraniDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load prvorangirani on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.prvorangirani).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
