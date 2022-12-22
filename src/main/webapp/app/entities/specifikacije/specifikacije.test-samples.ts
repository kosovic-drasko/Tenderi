import { ISpecifikacije, NewSpecifikacije } from './specifikacije.model';

export const sampleWithRequiredData: ISpecifikacije = {
  id: 59987,
  sifraPostupka: 94554,
  brojPartije: 41925,
  procijenjenaVrijednost: 4724,
  jedinicnaCijena: 82771,
};

export const sampleWithPartialData: ISpecifikacije = {
  id: 10987,
  sifraPostupka: 43372,
  brojPartije: 7056,
  atc: 'e-markets International',
  jacinaLijeka: 'Chicken Zloty',
  trazenaKolicina: 62636,
  pakovanje: 'Mexico',
  jedinicaMjere: 'Ergonomic Helena Administrator',
  procijenjenaVrijednost: 92160,
  jedinicnaCijena: 94942,
};

export const sampleWithFullData: ISpecifikacije = {
  id: 14002,
  sifraPostupka: 36456,
  brojPartije: 63317,
  atc: 'Electronics compress',
  inn: 'Generic Granite Down-sized',
  farmaceutskiOblikLijeka: 'SQL',
  jacinaLijeka: 'Throughway Buckinghamshire',
  trazenaKolicina: 95552,
  pakovanje: 'Cheese',
  jedinicaMjere: 'Movies upward-trending',
  procijenjenaVrijednost: 89755,
  jedinicnaCijena: 35958,
};

export const sampleWithNewData: NewSpecifikacije = {
  sifraPostupka: 38463,
  brojPartije: 67531,
  procijenjenaVrijednost: 33175,
  jedinicnaCijena: 33049,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
