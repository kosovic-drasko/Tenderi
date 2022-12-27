import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHvalePonude } from '../hvale-ponude.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../hvale-ponude.test-samples';

import { HvalePonudeService } from './hvale-ponude.service';

const requireRestSample: IHvalePonude = {
  ...sampleWithRequiredData,
};

describe('HvalePonude Service', () => {
  let service: HvalePonudeService;
  let httpMock: HttpTestingController;
  let expectedResult: IHvalePonude | IHvalePonude[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HvalePonudeService);
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

    it('should return a list of HvalePonude', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addHvalePonudeToCollectionIfMissing', () => {
      it('should add a HvalePonude to an empty array', () => {
        const hvalePonude: IHvalePonude = sampleWithRequiredData;
        expectedResult = service.addHvalePonudeToCollectionIfMissing([], hvalePonude);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hvalePonude);
      });

      it('should not add a HvalePonude to an array that contains it', () => {
        const hvalePonude: IHvalePonude = sampleWithRequiredData;
        const hvalePonudeCollection: IHvalePonude[] = [
          {
            ...hvalePonude,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHvalePonudeToCollectionIfMissing(hvalePonudeCollection, hvalePonude);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HvalePonude to an array that doesn't contain it", () => {
        const hvalePonude: IHvalePonude = sampleWithRequiredData;
        const hvalePonudeCollection: IHvalePonude[] = [sampleWithPartialData];
        expectedResult = service.addHvalePonudeToCollectionIfMissing(hvalePonudeCollection, hvalePonude);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hvalePonude);
      });

      it('should add only unique HvalePonude to an array', () => {
        const hvalePonudeArray: IHvalePonude[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hvalePonudeCollection: IHvalePonude[] = [sampleWithRequiredData];
        expectedResult = service.addHvalePonudeToCollectionIfMissing(hvalePonudeCollection, ...hvalePonudeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hvalePonude: IHvalePonude = sampleWithRequiredData;
        const hvalePonude2: IHvalePonude = sampleWithPartialData;
        expectedResult = service.addHvalePonudeToCollectionIfMissing([], hvalePonude, hvalePonude2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hvalePonude);
        expect(expectedResult).toContain(hvalePonude2);
      });

      it('should accept null and undefined values', () => {
        const hvalePonude: IHvalePonude = sampleWithRequiredData;
        expectedResult = service.addHvalePonudeToCollectionIfMissing([], null, hvalePonude, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hvalePonude);
      });

      it('should return initial array if no HvalePonude is added', () => {
        const hvalePonudeCollection: IHvalePonude[] = [sampleWithRequiredData];
        expectedResult = service.addHvalePonudeToCollectionIfMissing(hvalePonudeCollection, undefined, null);
        expect(expectedResult).toEqual(hvalePonudeCollection);
      });
    });

    describe('compareHvalePonude', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHvalePonude(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHvalePonude(entity1, entity2);
        const compareResult2 = service.compareHvalePonude(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHvalePonude(entity1, entity2);
        const compareResult2 = service.compareHvalePonude(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHvalePonude(entity1, entity2);
        const compareResult2 = service.compareHvalePonude(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
