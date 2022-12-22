import { IViewVrednovanje, NewViewVrednovanje } from './view-vrednovanje.model';

export const sampleWithRequiredData: IViewVrednovanje = {
  id: 55529,
};

export const sampleWithPartialData: IViewVrednovanje = {
  id: 15427,
  sifraPostupka: 32080,
  brojPartije: 21440,
  rokIsporuke: 91853,
  jedinicnaCijena: 10309,
  nazivPonudjaca: 'payment',
  atc: 'archive optimize Bedfordshire',
  procijenjenaVrijednost: 85137,
  vrstaPostupka: 'implement Avon',
  katekteristikaPonude: 'Intranet Metal',
  karakteristikaSpecifikacije: 'Corporate Function-based',
  bodCijena: 42869,
  bodRok: 3043,
  bodUkupno: 79539,
};

export const sampleWithFullData: IViewVrednovanje = {
  id: 13988,
  sifraPostupka: 2581,
  sifraPonude: 28710,
  brojPartije: 13966,
  nazivProizvodjaca: 'Intranet Summit',
  zasticeniNaziv: 'Regional',
  ponudjenaVrijednost: 36422,
  rokIsporuke: 99441,
  jedinicnaCijena: 11296,
  nazivPonudjaca: 'Ngultrum Executive yellow',
  atc: 'impactful generate',
  trazenaKolicina: 59380,
  procijenjenaVrijednost: 57292,
  vrstaPostupka: 'interfaces',
  katekteristikaPonude: 'olive transform Managed',
  karakteristikaSpecifikacije: 'Bedfordshire',
  bodCijena: 8231,
  bodRok: 39400,
  bodUkupno: 39853,
};

export const sampleWithNewData: NewViewVrednovanje = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
