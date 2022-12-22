jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PonudjaciService } from '../service/ponudjaci.service';

import { PonudjaciDeleteDialogComponent } from './ponudjaci-delete-dialog.component';

describe('Ponudjaci Management Delete Component', () => {
  let comp: PonudjaciDeleteDialogComponent;
  let fixture: ComponentFixture<PonudjaciDeleteDialogComponent>;
  let service: PonudjaciService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PonudjaciDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PonudjaciDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PonudjaciDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PonudjaciService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
