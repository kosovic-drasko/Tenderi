import { IPonude, NewPonude } from './ponude.model';

export const sampleWithRequiredData: IPonude = {
  id: 94075,
  sifraPostupka: 25624,
  sifraPonude: 43998,
  brojPartije: 80246,
  ponudjenaVrijednost: 18270,
};

export const sampleWithPartialData: IPonude = {
  id: 80966,
  sifraPostupka: 97633,
  sifraPonude: 37036,
  brojPartije: 40699,
  nazivPonudjaca: 'Dinar grey Planner',
  zasticeniNaziv: 'interface Monitored Account',
  ponudjenaVrijednost: 44847,
  rokIsporuke: 610,
  jedinicnaCijena: 55121,
  sifraPonudjaca: 57818,
  karakteristika: 'indigo',
};

export const sampleWithFullData: IPonude = {
  id: 37135,
  sifraPostupka: 65528,
  sifraPonude: 25324,
  brojPartije: 94710,
  nazivProizvodjaca: 'Michigan ubiquitous',
  nazivPonudjaca: 'Row driver Senior',
  zasticeniNaziv: 'Account Refined',
  ponudjenaVrijednost: 92092,
  rokIsporuke: 11789,
  jedinicnaCijena: 30894,
  selected: true,
  sifraPonudjaca: 2171,
  karakteristika: 'programming Belize Account',
};

export const sampleWithNewData: NewPonude = {
  sifraPostupka: 50678,
  sifraPonude: 75381,
  brojPartije: 58285,
  ponudjenaVrijednost: 92446,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
