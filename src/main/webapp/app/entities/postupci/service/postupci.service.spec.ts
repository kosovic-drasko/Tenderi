import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPostupci } from '../postupci.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../postupci.test-samples';

import { PostupciService, RestPostupci } from './postupci.service';

const requireRestSample: RestPostupci = {
  ...sampleWithRequiredData,
  datumObjave: sampleWithRequiredData.datumObjave?.format(DATE_FORMAT),
  datumOtvaranja: sampleWithRequiredData.datumOtvaranja?.format(DATE_FORMAT),
};

describe('Postupci Service', () => {
  let service: PostupciService;
  let httpMock: HttpTestingController;
  let expectedResult: IPostupci | IPostupci[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PostupciService);
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

    it('should create a Postupci', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const postupci = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(postupci).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Postupci', () => {
      const postupci = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(postupci).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Postupci', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Postupci', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Postupci', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPostupciToCollectionIfMissing', () => {
      it('should add a Postupci to an empty array', () => {
        const postupci: IPostupci = sampleWithRequiredData;
        expectedResult = service.addPostupciToCollectionIfMissing([], postupci);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postupci);
      });

      it('should not add a Postupci to an array that contains it', () => {
        const postupci: IPostupci = sampleWithRequiredData;
        const postupciCollection: IPostupci[] = [
          {
            ...postupci,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPostupciToCollectionIfMissing(postupciCollection, postupci);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Postupci to an array that doesn't contain it", () => {
        const postupci: IPostupci = sampleWithRequiredData;
        const postupciCollection: IPostupci[] = [sampleWithPartialData];
        expectedResult = service.addPostupciToCollectionIfMissing(postupciCollection, postupci);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postupci);
      });

      it('should add only unique Postupci to an array', () => {
        const postupciArray: IPostupci[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const postupciCollection: IPostupci[] = [sampleWithRequiredData];
        expectedResult = service.addPostupciToCollectionIfMissing(postupciCollection, ...postupciArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const postupci: IPostupci = sampleWithRequiredData;
        const postupci2: IPostupci = sampleWithPartialData;
        expectedResult = service.addPostupciToCollectionIfMissing([], postupci, postupci2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postupci);
        expect(expectedResult).toContain(postupci2);
      });

      it('should accept null and undefined values', () => {
        const postupci: IPostupci = sampleWithRequiredData;
        expectedResult = service.addPostupciToCollectionIfMissing([], null, postupci, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postupci);
      });

      it('should return initial array if no Postupci is added', () => {
        const postupciCollection: IPostupci[] = [sampleWithRequiredData];
        expectedResult = service.addPostupciToCollectionIfMissing(postupciCollection, undefined, null);
        expect(expectedResult).toEqual(postupciCollection);
      });
    });

    describe('comparePostupci', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePostupci(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePostupci(entity1, entity2);
        const compareResult2 = service.comparePostupci(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePostupci(entity1, entity2);
        const compareResult2 = service.comparePostupci(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePostupci(entity1, entity2);
        const compareResult2 = service.comparePostupci(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
