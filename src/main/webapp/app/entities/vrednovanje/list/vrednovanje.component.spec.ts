import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VrednovanjeService } from '../service/vrednovanje.service';

import { VrednovanjeComponent } from './vrednovanje.component';

describe('Vrednovanje Management Component', () => {
  let comp: VrednovanjeComponent;
  let fixture: ComponentFixture<VrednovanjeComponent>;
  let service: VrednovanjeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'vrednovanje', component: VrednovanjeComponent }]), HttpClientTestingModule],
      declarations: [VrednovanjeComponent],
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
      .overrideTemplate(VrednovanjeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VrednovanjeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VrednovanjeService);

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
    expect(comp.vrednovanjes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to vrednovanjeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getVrednovanjeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getVrednovanjeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
