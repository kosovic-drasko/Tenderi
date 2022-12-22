import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { HvalePonudeService } from '../service/hvale-ponude.service';

import { HvalePonudeComponent } from './hvale-ponude.component';

describe('HvalePonude Management Component', () => {
  let comp: HvalePonudeComponent;
  let fixture: ComponentFixture<HvalePonudeComponent>;
  let service: HvalePonudeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'hvale-ponude', component: HvalePonudeComponent }]), HttpClientTestingModule],
      declarations: [HvalePonudeComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(HvalePonudeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HvalePonudeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HvalePonudeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.hvalePonudes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to hvalePonudeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getHvalePonudeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getHvalePonudeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
