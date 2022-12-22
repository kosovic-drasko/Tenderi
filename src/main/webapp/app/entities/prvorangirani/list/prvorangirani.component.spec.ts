import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrvorangiraniService } from '../service/prvorangirani.service';

import { PrvorangiraniComponent } from './prvorangirani.component';

describe('Prvorangirani Management Component', () => {
  let comp: PrvorangiraniComponent;
  let fixture: ComponentFixture<PrvorangiraniComponent>;
  let service: PrvorangiraniService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'prvorangirani', component: PrvorangiraniComponent }]), HttpClientTestingModule],
      declarations: [PrvorangiraniComponent],
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
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PrvorangiraniComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrvorangiraniComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrvorangiraniService);

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
    expect(comp.prvorangiranis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to prvorangiraniService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPrvorangiraniIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPrvorangiraniIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
