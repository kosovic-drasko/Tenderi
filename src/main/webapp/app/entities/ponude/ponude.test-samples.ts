import { IPonude, NewPonude } from './ponude.model';

export const sampleWithRequiredData: IPonude = {
  id: 94075,
  sifraPostupka: 25624,
  sifraPonude: 43998,
  brojPartije: 80246,
  ponudjenaVrijednost: 18270,
};

export const sampleWithPartialData: IPonude = {
  id: 91419,
  sifraPostupka: 80966,
  sifraPonude: 97633,
  brojPartije: 37036,
  zasticeniNaziv: 'feed Bedfordshire',
  ponudjenaVrijednost: 56935,
  rokIsporuke: 99552,
  jedinicnaCijena: 58766,
  selected: true,
  karakteristika: 'interface Monitored Account',
};

export const sampleWithFullData: IPonude = {
  id: 44847,
  sifraPostupka: 610,
  sifraPonude: 55121,
  brojPartije: 57818,
  nazivProizvodjaca: 'indigo',
  zasticeniNaziv: 'withdrawal Parkways',
  ponudjenaVrijednost: 82376,
  rokIsporuke: 33646,
  jedinicnaCijena: 91164,
  selected: true,
  sifraPonudjaca: 88690,
  karakteristika: 'capacitor Outdoors',
};

export const sampleWithNewData: NewPonude = {
  sifraPostupka: 6477,
  sifraPonude: 30796,
  brojPartije: 64810,
  ponudjenaVrijednost: 59443,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
