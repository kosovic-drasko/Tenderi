import { IViewPrvorangirani, NewViewPrvorangirani } from './view-prvorangirani.model';

export const sampleWithRequiredData: IViewPrvorangirani = {
  id: 72832,
};

export const sampleWithPartialData: IViewPrvorangirani = {
  id: 68522,
  sifraPonude: 55545,
  trazenaKolicina: 20259,
  nazivProizvodjaca: 'exuding',
  jedinicnaCijena: 99909,
  ponudjenaVrijednost: 5992,
  vrstaPostupka: 'Awesome CSS',
  bodCijena: 46219,
  bodRok: 92654,
  bodUkupno: 2348,
  karakteristikaSpecifikacije: 'Borders',
  karakteristikaPonude: 'project Identity',
};

export const sampleWithFullData: IViewPrvorangirani = {
  id: 79444,
  sifraPostupka: 24191,
  nazivPonudjaca: 'innovate',
  sifraPonude: 93991,
  atc: 'Buckinghamshire',
  trazenaKolicina: 47697,
  procijenjenaVrijednost: 83635,
  nazivProizvodjaca: 'pixel',
  zasticeniNaziv: 'Sausages Generic',
  jedinicnaCijena: 14967,
  ponudjenaVrijednost: 87389,
  rokIsporuke: 38786,
  vrstaPostupka: 'Customer Towels',
  bodCijena: 87736,
  bodRok: 19526,
  bodUkupno: 66621,
  karakteristikaSpecifikacije: 'transmitting',
  karakteristikaPonude: 'Incredible reboot',
};

export const sampleWithNewData: NewViewPrvorangirani = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
