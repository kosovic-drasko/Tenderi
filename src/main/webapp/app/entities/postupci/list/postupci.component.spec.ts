import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PostupciService } from '../service/postupci.service';

import { PostupciComponent } from './postupci.component';

describe('Postupci Management Component', () => {
  let comp: PostupciComponent;
  let fixture: ComponentFixture<PostupciComponent>;
  let service: PostupciService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'postupci', component: PostupciComponent }]), HttpClientTestingModule],
      declarations: [PostupciComponent],
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
      .overrideTemplate(PostupciComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PostupciComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PostupciService);

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
    expect(comp.postupcis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to postupciService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPostupciIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPostupciIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
