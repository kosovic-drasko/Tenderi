import { IHvalePonude, NewHvalePonude } from './hvale-ponude.model';

export const sampleWithRequiredData: IHvalePonude = {
  id: 48282,
};

export const sampleWithPartialData: IHvalePonude = {
  id: 21933,
  inn: 'firewall eco-centric',
};

export const sampleWithFullData: IHvalePonude = {
  id: 25989,
  sifraPostupka: 76727,
  brojPartije: 20331,
  inn: 'Books',
  farmaceutskiOblikLijeka: 'AI Auto',
  pakovanje: 'Technician',
  trazenaKolicina: 91459,
  procijenjenaVrijednost: 34045,
};

export const sampleWithNewData: NewHvalePonude = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
