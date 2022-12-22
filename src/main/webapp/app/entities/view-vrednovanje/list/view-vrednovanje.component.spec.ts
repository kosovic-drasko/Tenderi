import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ViewVrednovanjeService } from '../service/view-vrednovanje.service';

import { ViewVrednovanjeComponent } from './view-vrednovanje.component';

describe('ViewVrednovanje Management Component', () => {
  let comp: ViewVrednovanjeComponent;
  let fixture: ComponentFixture<ViewVrednovanjeComponent>;
  let service: ViewVrednovanjeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'view-vrednovanje', component: ViewVrednovanjeComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ViewVrednovanjeComponent],
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
      .overrideTemplate(ViewVrednovanjeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ViewVrednovanjeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ViewVrednovanjeService);

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
    expect(comp.viewVrednovanjes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to viewVrednovanjeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getViewVrednovanjeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getViewVrednovanjeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
