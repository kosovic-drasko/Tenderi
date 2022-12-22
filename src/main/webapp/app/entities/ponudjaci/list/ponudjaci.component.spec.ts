import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PonudjaciService } from '../service/ponudjaci.service';

import { PonudjaciComponent } from './ponudjaci.component';

describe('Ponudjaci Management Component', () => {
  let comp: PonudjaciComponent;
  let fixture: ComponentFixture<PonudjaciComponent>;
  let service: PonudjaciService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'ponudjaci', component: PonudjaciComponent }]), HttpClientTestingModule],
      declarations: [PonudjaciComponent],
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
      .overrideTemplate(PonudjaciComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PonudjaciComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PonudjaciService);

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
    expect(comp.ponudjacis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to ponudjaciService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPonudjaciIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPonudjaciIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
