import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVrednovanje } from '../vrednovanje.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../vrednovanje.test-samples';

import { VrednovanjeService } from './vrednovanje.service';

const requireRestSample: IVrednovanje = {
  ...sampleWithRequiredData,
};

describe('Vrednovanje Service', () => {
  let service: VrednovanjeService;
  let httpMock: HttpTestingController;
  let expectedResult: IVrednovanje | IVrednovanje[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VrednovanjeService);
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

    it('should return a list of Vrednovanje', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addVrednovanjeToCollectionIfMissing', () => {
      it('should add a Vrednovanje to an empty array', () => {
        const vrednovanje: IVrednovanje = sampleWithRequiredData;
        expectedResult = service.addVrednovanjeToCollectionIfMissing([], vrednovanje);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vrednovanje);
      });

      it('should not add a Vrednovanje to an array that contains it', () => {
        const vrednovanje: IVrednovanje = sampleWithRequiredData;
        const vrednovanjeCollection: IVrednovanje[] = [
          {
            ...vrednovanje,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVrednovanjeToCollectionIfMissing(vrednovanjeCollection, vrednovanje);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Vrednovanje to an array that doesn't contain it", () => {
        const vrednovanje: IVrednovanje = sampleWithRequiredData;
        const vrednovanjeCollection: IVrednovanje[] = [sampleWithPartialData];
        expectedResult = service.addVrednovanjeToCollectionIfMissing(vrednovanjeCollection, vrednovanje);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vrednovanje);
      });

      it('should add only unique Vrednovanje to an array', () => {
        const vrednovanjeArray: IVrednovanje[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const vrednovanjeCollection: IVrednovanje[] = [sampleWithRequiredData];
        expectedResult = service.addVrednovanjeToCollectionIfMissing(vrednovanjeCollection, ...vrednovanjeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vrednovanje: IVrednovanje = sampleWithRequiredData;
        const vrednovanje2: IVrednovanje = sampleWithPartialData;
        expectedResult = service.addVrednovanjeToCollectionIfMissing([], vrednovanje, vrednovanje2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vrednovanje);
        expect(expectedResult).toContain(vrednovanje2);
      });

      it('should accept null and undefined values', () => {
        const vrednovanje: IVrednovanje = sampleWithRequiredData;
        expectedResult = service.addVrednovanjeToCollectionIfMissing([], null, vrednovanje, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vrednovanje);
      });

      it('should return initial array if no Vrednovanje is added', () => {
        const vrednovanjeCollection: IVrednovanje[] = [sampleWithRequiredData];
        expectedResult = service.addVrednovanjeToCollectionIfMissing(vrednovanjeCollection, undefined, null);
        expect(expectedResult).toEqual(vrednovanjeCollection);
      });
    });

    describe('compareVrednovanje', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVrednovanje(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVrednovanje(entity1, entity2);
        const compareResult2 = service.compareVrednovanje(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVrednovanje(entity1, entity2);
        const compareResult2 = service.compareVrednovanje(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVrednovanje(entity1, entity2);
        const compareResult2 = service.compareVrednovanje(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
