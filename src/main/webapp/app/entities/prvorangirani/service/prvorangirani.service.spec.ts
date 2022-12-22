import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrvorangirani } from '../prvorangirani.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prvorangirani.test-samples';

import { PrvorangiraniService } from './prvorangirani.service';

const requireRestSample: IPrvorangirani = {
  ...sampleWithRequiredData,
};

describe('Prvorangirani Service', () => {
  let service: PrvorangiraniService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrvorangirani | IPrvorangirani[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrvorangiraniService);
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

    it('should return a list of Prvorangirani', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addPrvorangiraniToCollectionIfMissing', () => {
      it('should add a Prvorangirani to an empty array', () => {
        const prvorangirani: IPrvorangirani = sampleWithRequiredData;
        expectedResult = service.addPrvorangiraniToCollectionIfMissing([], prvorangirani);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prvorangirani);
      });

      it('should not add a Prvorangirani to an array that contains it', () => {
        const prvorangirani: IPrvorangirani = sampleWithRequiredData;
        const prvorangiraniCollection: IPrvorangirani[] = [
          {
            ...prvorangirani,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrvorangiraniToCollectionIfMissing(prvorangiraniCollection, prvorangirani);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Prvorangirani to an array that doesn't contain it", () => {
        const prvorangirani: IPrvorangirani = sampleWithRequiredData;
        const prvorangiraniCollection: IPrvorangirani[] = [sampleWithPartialData];
        expectedResult = service.addPrvorangiraniToCollectionIfMissing(prvorangiraniCollection, prvorangirani);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prvorangirani);
      });

      it('should add only unique Prvorangirani to an array', () => {
        const prvorangiraniArray: IPrvorangirani[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const prvorangiraniCollection: IPrvorangirani[] = [sampleWithRequiredData];
        expectedResult = service.addPrvorangiraniToCollectionIfMissing(prvorangiraniCollection, ...prvorangiraniArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prvorangirani: IPrvorangirani = sampleWithRequiredData;
        const prvorangirani2: IPrvorangirani = sampleWithPartialData;
        expectedResult = service.addPrvorangiraniToCollectionIfMissing([], prvorangirani, prvorangirani2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prvorangirani);
        expect(expectedResult).toContain(prvorangirani2);
      });

      it('should accept null and undefined values', () => {
        const prvorangirani: IPrvorangirani = sampleWithRequiredData;
        expectedResult = service.addPrvorangiraniToCollectionIfMissing([], null, prvorangirani, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prvorangirani);
      });

      it('should return initial array if no Prvorangirani is added', () => {
        const prvorangiraniCollection: IPrvorangirani[] = [sampleWithRequiredData];
        expectedResult = service.addPrvorangiraniToCollectionIfMissing(prvorangiraniCollection, undefined, null);
        expect(expectedResult).toEqual(prvorangiraniCollection);
      });
    });

    describe('comparePrvorangirani', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrvorangirani(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrvorangirani(entity1, entity2);
        const compareResult2 = service.comparePrvorangirani(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrvorangirani(entity1, entity2);
        const compareResult2 = service.comparePrvorangirani(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrvorangirani(entity1, entity2);
        const compareResult2 = service.comparePrvorangirani(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
