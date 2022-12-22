import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPonudjaci } from '../ponudjaci.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ponudjaci.test-samples';

import { PonudjaciService } from './ponudjaci.service';

const requireRestSample: IPonudjaci = {
  ...sampleWithRequiredData,
};

describe('Ponudjaci Service', () => {
  let service: PonudjaciService;
  let httpMock: HttpTestingController;
  let expectedResult: IPonudjaci | IPonudjaci[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PonudjaciService);
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

    it('should create a Ponudjaci', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ponudjaci = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ponudjaci).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ponudjaci', () => {
      const ponudjaci = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ponudjaci).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ponudjaci', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ponudjaci', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Ponudjaci', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPonudjaciToCollectionIfMissing', () => {
      it('should add a Ponudjaci to an empty array', () => {
        const ponudjaci: IPonudjaci = sampleWithRequiredData;
        expectedResult = service.addPonudjaciToCollectionIfMissing([], ponudjaci);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ponudjaci);
      });

      it('should not add a Ponudjaci to an array that contains it', () => {
        const ponudjaci: IPonudjaci = sampleWithRequiredData;
        const ponudjaciCollection: IPonudjaci[] = [
          {
            ...ponudjaci,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPonudjaciToCollectionIfMissing(ponudjaciCollection, ponudjaci);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ponudjaci to an array that doesn't contain it", () => {
        const ponudjaci: IPonudjaci = sampleWithRequiredData;
        const ponudjaciCollection: IPonudjaci[] = [sampleWithPartialData];
        expectedResult = service.addPonudjaciToCollectionIfMissing(ponudjaciCollection, ponudjaci);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ponudjaci);
      });

      it('should add only unique Ponudjaci to an array', () => {
        const ponudjaciArray: IPonudjaci[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ponudjaciCollection: IPonudjaci[] = [sampleWithRequiredData];
        expectedResult = service.addPonudjaciToCollectionIfMissing(ponudjaciCollection, ...ponudjaciArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ponudjaci: IPonudjaci = sampleWithRequiredData;
        const ponudjaci2: IPonudjaci = sampleWithPartialData;
        expectedResult = service.addPonudjaciToCollectionIfMissing([], ponudjaci, ponudjaci2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ponudjaci);
        expect(expectedResult).toContain(ponudjaci2);
      });

      it('should accept null and undefined values', () => {
        const ponudjaci: IPonudjaci = sampleWithRequiredData;
        expectedResult = service.addPonudjaciToCollectionIfMissing([], null, ponudjaci, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ponudjaci);
      });

      it('should return initial array if no Ponudjaci is added', () => {
        const ponudjaciCollection: IPonudjaci[] = [sampleWithRequiredData];
        expectedResult = service.addPonudjaciToCollectionIfMissing(ponudjaciCollection, undefined, null);
        expect(expectedResult).toEqual(ponudjaciCollection);
      });
    });

    describe('comparePonudjaci', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePonudjaci(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePonudjaci(entity1, entity2);
        const compareResult2 = service.comparePonudjaci(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePonudjaci(entity1, entity2);
        const compareResult2 = service.comparePonudjaci(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePonudjaci(entity1, entity2);
        const compareResult2 = service.comparePonudjaci(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
