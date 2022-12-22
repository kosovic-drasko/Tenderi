import { IVrednovanje, NewVrednovanje } from './vrednovanje.model';

export const sampleWithRequiredData: IVrednovanje = {
  id: 95302,
};

export const sampleWithPartialData: IVrednovanje = {
  id: 64079,
  sifraPonude: 32885,
  ponudjenaVrijednost: 29454,
  rokIsporuke: 88755,
  nazivPonudjaca: 'Ball Vista',
  atc: 'Metal',
  bodCijena: 30502,
  bodUkupno: 69552,
};

export const sampleWithFullData: IVrednovanje = {
  id: 78878,
  sifraPostupka: 99038,
  sifraPonude: 9273,
  brojPartije: 56040,
  nazivProizvodjaca: 'Manager magenta calculating',
  zasticeniNaziv: 'Accountability',
  ponudjenaVrijednost: 19856,
  rokIsporuke: 48030,
  jedinicnaCijena: 83325,
  nazivPonudjaca: 'Glen',
  atc: 'matrices networks',
  trazenaKolicina: 42459,
  procijenjenaVrijednost: 68116,
  vrstaPostupka: 'hack Bhutanese Account',
  bodCijena: 2638,
  bodRok: 62758,
  bodUkupno: 52742,
};

export const sampleWithNewData: NewVrednovanje = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
