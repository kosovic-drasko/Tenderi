import { IViewPonude, NewViewPonude } from './view-ponude.model';

export const sampleWithRequiredData: IViewPonude = {
  id: 8069,
  sifraPostupka: 33285,
  sifraPonude: 55417,
  brojPartije: 55003,
  ponudjenaVrijednost: 52723,
};

export const sampleWithPartialData: IViewPonude = {
  id: 83544,
  sifraPostupka: 11701,
  sifraPonude: 12997,
  brojPartije: 18769,
  nazivProizvodjaca: 'Strategist Peso hardware',
  zasticeniNaziv: 'South cohesive',
  ponudjenaVrijednost: 29437,
  jedinicnaCijena: 36623,
  karakteristika: 'Pants New transmitter',
};

export const sampleWithFullData: IViewPonude = {
  id: 53386,
  sifraPostupka: 45562,
  sifraPonude: 26774,
  brojPartije: 62460,
  nazivProizvodjaca: 'rich demand-driven',
  zasticeniNaziv: 'Liaison Lead Soap',
  ponudjenaVrijednost: 87048,
  rokIsporuke: 79992,
  jedinicnaCijena: 23109,
  selected: false,
  nazivPonudjaca: 'hacking Electronics Kwanza',
  karakteristika: 'Markets Dalasi Wooden',
};

export const sampleWithNewData: NewViewPonude = {
  sifraPostupka: 89929,
  sifraPonude: 28118,
  brojPartije: 18092,
  ponudjenaVrijednost: 45163,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
