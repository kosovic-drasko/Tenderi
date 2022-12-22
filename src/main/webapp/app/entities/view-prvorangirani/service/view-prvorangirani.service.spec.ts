import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IViewPrvorangirani } from '../view-prvorangirani.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../view-prvorangirani.test-samples';

import { ViewPrvorangiraniService } from './view-prvorangirani.service';

const requireRestSample: IViewPrvorangirani = {
  ...sampleWithRequiredData,
};

describe('ViewPrvorangirani Service', () => {
  let service: ViewPrvorangiraniService;
  let httpMock: HttpTestingController;
  let expectedResult: IViewPrvorangirani | IViewPrvorangirani[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ViewPrvorangiraniService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ViewPrvorangirani', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addViewPrvorangiraniToCollectionIfMissing', () => {
      it('should add a ViewPrvorangirani to an empty array', () => {
        const viewPrvorangirani: IViewPrvorangirani = sampleWithRequiredData;
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing([], viewPrvorangirani);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(viewPrvorangirani);
      });

      it('should not add a ViewPrvorangirani to an array that contains it', () => {
        const viewPrvorangirani: IViewPrvorangirani = sampleWithRequiredData;
        const viewPrvorangiraniCollection: IViewPrvorangirani[] = [
          {
            ...viewPrvorangirani,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing(viewPrvorangiraniCollection, viewPrvorangirani);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ViewPrvorangirani to an array that doesn't contain it", () => {
        const viewPrvorangirani: IViewPrvorangirani = sampleWithRequiredData;
        const viewPrvorangiraniCollection: IViewPrvorangirani[] = [sampleWithPartialData];
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing(viewPrvorangiraniCollection, viewPrvorangirani);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(viewPrvorangirani);
      });

      it('should add only unique ViewPrvorangirani to an array', () => {
        const viewPrvorangiraniArray: IViewPrvorangirani[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const viewPrvorangiraniCollection: IViewPrvorangirani[] = [sampleWithRequiredData];
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing(viewPrvorangiraniCollection, ...viewPrvorangiraniArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const viewPrvorangirani: IViewPrvorangirani = sampleWithRequiredData;
        const viewPrvorangirani2: IViewPrvorangirani = sampleWithPartialData;
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing([], viewPrvorangirani, viewPrvorangirani2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(viewPrvorangirani);
        expect(expectedResult).toContain(viewPrvorangirani2);
      });

      it('should accept null and undefined values', () => {
        const viewPrvorangirani: IViewPrvorangirani = sampleWithRequiredData;
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing([], null, viewPrvorangirani, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(viewPrvorangirani);
      });

      it('should return initial array if no ViewPrvorangirani is added', () => {
        const viewPrvorangiraniCollection: IViewPrvorangirani[] = [sampleWithRequiredData];
        expectedResult = service.addViewPrvorangiraniToCollectionIfMissing(viewPrvorangiraniCollection, undefined, null);
        expect(expectedResult).toEqual(viewPrvorangiraniCollection);
      });
    });

    describe('compareViewPrvorangirani', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareViewPrvorangirani(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareViewPrvorangirani(entity1, entity2);
        const compareResult2 = service.compareViewPrvorangirani(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareViewPrvorangirani(entity1, entity2);
        const compareResult2 = service.compareViewPrvorangirani(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareViewPrvorangirani(entity1, entity2);
        const compareResult2 = service.compareViewPrvorangirani(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
