import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITenderiHome } from '../tenderi-home.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tenderi-home.test-samples';

import { TenderiHomeService } from './tenderi-home.service';

const requireRestSample: ITenderiHome = {
  ...sampleWithRequiredData,
};

describe('TenderiHome Service', () => {
  let service: TenderiHomeService;
  let httpMock: HttpTestingController;
  let expectedResult: ITenderiHome | ITenderiHome[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TenderiHomeService);
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

    it('should return a list of TenderiHome', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addTenderiHomeToCollectionIfMissing', () => {
      it('should add a TenderiHome to an empty array', () => {
        const tenderiHome: ITenderiHome = sampleWithRequiredData;
        expectedResult = service.addTenderiHomeToCollectionIfMissing([], tenderiHome);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tenderiHome);
      });

      it('should not add a TenderiHome to an array that contains it', () => {
        const tenderiHome: ITenderiHome = sampleWithRequiredData;
        const tenderiHomeCollection: ITenderiHome[] = [
          {
            ...tenderiHome,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTenderiHomeToCollectionIfMissing(tenderiHomeCollection, tenderiHome);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TenderiHome to an array that doesn't contain it", () => {
        const tenderiHome: ITenderiHome = sampleWithRequiredData;
        const tenderiHomeCollection: ITenderiHome[] = [sampleWithPartialData];
        expectedResult = service.addTenderiHomeToCollectionIfMissing(tenderiHomeCollection, tenderiHome);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tenderiHome);
      });

      it('should add only unique TenderiHome to an array', () => {
        const tenderiHomeArray: ITenderiHome[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tenderiHomeCollection: ITenderiHome[] = [sampleWithRequiredData];
        expectedResult = service.addTenderiHomeToCollectionIfMissing(tenderiHomeCollection, ...tenderiHomeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tenderiHome: ITenderiHome = sampleWithRequiredData;
        const tenderiHome2: ITenderiHome = sampleWithPartialData;
        expectedResult = service.addTenderiHomeToCollectionIfMissing([], tenderiHome, tenderiHome2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tenderiHome);
        expect(expectedResult).toContain(tenderiHome2);
      });

      it('should accept null and undefined values', () => {
        const tenderiHome: ITenderiHome = sampleWithRequiredData;
        expectedResult = service.addTenderiHomeToCollectionIfMissing([], null, tenderiHome, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tenderiHome);
      });

      it('should return initial array if no TenderiHome is added', () => {
        const tenderiHomeCollection: ITenderiHome[] = [sampleWithRequiredData];
        expectedResult = service.addTenderiHomeToCollectionIfMissing(tenderiHomeCollection, undefined, null);
        expect(expectedResult).toEqual(tenderiHomeCollection);
      });
    });

    describe('compareTenderiHome', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTenderiHome(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTenderiHome(entity1, entity2);
        const compareResult2 = service.compareTenderiHome(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTenderiHome(entity1, entity2);
        const compareResult2 = service.compareTenderiHome(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTenderiHome(entity1, entity2);
        const compareResult2 = service.compareTenderiHome(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
