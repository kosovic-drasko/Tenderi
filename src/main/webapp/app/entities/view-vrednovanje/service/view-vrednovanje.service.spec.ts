import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IViewVrednovanje } from '../view-vrednovanje.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../view-vrednovanje.test-samples';

import { ViewVrednovanjeService } from './view-vrednovanje.service';

const requireRestSample: IViewVrednovanje = {
  ...sampleWithRequiredData,
};

describe('ViewVrednovanje Service', () => {
  let service: ViewVrednovanjeService;
  let httpMock: HttpTestingController;
  let expectedResult: IViewVrednovanje | IViewVrednovanje[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ViewVrednovanjeService);
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

    it('should return a list of ViewVrednovanje', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addViewVrednovanjeToCollectionIfMissing', () => {
      it('should add a ViewVrednovanje to an empty array', () => {
        const viewVrednovanje: IViewVrednovanje = sampleWithRequiredData;
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing([], viewVrednovanje);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(viewVrednovanje);
      });

      it('should not add a ViewVrednovanje to an array that contains it', () => {
        const viewVrednovanje: IViewVrednovanje = sampleWithRequiredData;
        const viewVrednovanjeCollection: IViewVrednovanje[] = [
          {
            ...viewVrednovanje,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, viewVrednovanje);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ViewVrednovanje to an array that doesn't contain it", () => {
        const viewVrednovanje: IViewVrednovanje = sampleWithRequiredData;
        const viewVrednovanjeCollection: IViewVrednovanje[] = [sampleWithPartialData];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, viewVrednovanje);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(viewVrednovanje);
      });

      it('should add only unique ViewVrednovanje to an array', () => {
        const viewVrednovanjeArray: IViewVrednovanje[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const viewVrednovanjeCollection: IViewVrednovanje[] = [sampleWithRequiredData];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, ...viewVrednovanjeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const viewVrednovanje: IViewVrednovanje = sampleWithRequiredData;
        const viewVrednovanje2: IViewVrednovanje = sampleWithPartialData;
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing([], viewVrednovanje, viewVrednovanje2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(viewVrednovanje);
        expect(expectedResult).toContain(viewVrednovanje2);
      });

      it('should accept null and undefined values', () => {
        const viewVrednovanje: IViewVrednovanje = sampleWithRequiredData;
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing([], null, viewVrednovanje, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(viewVrednovanje);
      });

      it('should return initial array if no ViewVrednovanje is added', () => {
        const viewVrednovanjeCollection: IViewVrednovanje[] = [sampleWithRequiredData];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, undefined, null);
        expect(expectedResult).toEqual(viewVrednovanjeCollection);
      });
    });

    describe('compareViewVrednovanje', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareViewVrednovanje(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareViewVrednovanje(entity1, entity2);
        const compareResult2 = service.compareViewVrednovanje(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareViewVrednovanje(entity1, entity2);
        const compareResult2 = service.compareViewVrednovanje(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareViewVrednovanje(entity1, entity2);
        const compareResult2 = service.compareViewVrednovanje(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
