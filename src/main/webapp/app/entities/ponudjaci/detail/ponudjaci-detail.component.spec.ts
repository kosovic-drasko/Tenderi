import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PonudjaciDetailComponent } from './ponudjaci-detail.component';

describe('Ponudjaci Management Detail Component', () => {
  let comp: PonudjaciDetailComponent;
  let fixture: ComponentFixture<PonudjaciDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PonudjaciDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ponudjaci: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PonudjaciDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PonudjaciDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ponudjaci on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ponudjaci).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
