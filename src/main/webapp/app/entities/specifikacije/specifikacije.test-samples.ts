import { ISpecifikacije, NewSpecifikacije } from './specifikacije.model';

export const sampleWithRequiredData: ISpecifikacije = {
  id: 59987,
  sifraPostupka: 94554,
  brojPartije: 41925,
  procijenjenaVrijednost: 4724,
  jedinicnaCijena: 82771,
};

export const sampleWithPartialData: ISpecifikacije = {
  id: 43372,
  sifraPostupka: 7056,
  brojPartije: 42090,
  atc: 'Account Dollar',
  jacinaLijeka: 'haptic',
  trazenaKolicina: 95964,
  pakovanje: 'deposit Awesome Namibia',
  jedinicaMjere: 'wireless',
  procijenjenaVrijednost: 51019,
  jedinicnaCijena: 96901,
};

export const sampleWithFullData: ISpecifikacije = {
  id: 40692,
  sifraPostupka: 28205,
  brojPartije: 92160,
  atc: 'Cotton web-readiness input',
  inn: 'bypass navigating Granite',
  farmaceutskiOblikLijeka: 'mint',
  jacinaLijeka: 'orchestration Throughway Buckinghamshire',
  trazenaKolicina: 95552,
  pakovanje: 'Cheese',
  jedinicaMjere: 'Movies upward-trending',
  procijenjenaVrijednost: 89755,
  jedinicnaCijena: 35958,
  karakteristika: 'Croatian Functionality',
};

export const sampleWithNewData: NewSpecifikacije = {
  sifraPostupka: 34255,
  brojPartije: 30258,
  procijenjenaVrijednost: 81202,
  jedinicnaCijena: 45921,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
