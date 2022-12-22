import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ViewPrvorangiraniService } from '../service/view-prvorangirani.service';

import { ViewPrvorangiraniComponent } from './view-prvorangirani.component';

describe('ViewPrvorangirani Management Component', () => {
  let comp: ViewPrvorangiraniComponent;
  let fixture: ComponentFixture<ViewPrvorangiraniComponent>;
  let service: ViewPrvorangiraniService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'view-prvorangirani', component: ViewPrvorangiraniComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ViewPrvorangiraniComponent],
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
      .overrideTemplate(ViewPrvorangiraniComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ViewPrvorangiraniComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ViewPrvorangiraniService);

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
    expect(comp.viewPrvorangiranis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to viewPrvorangiraniService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getViewPrvorangiraniIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getViewPrvorangiraniIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
