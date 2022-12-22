import { IPrvorangirani, NewPrvorangirani } from './prvorangirani.model';

export const sampleWithRequiredData: IPrvorangirani = {
  id: 19648,
};

export const sampleWithPartialData: IPrvorangirani = {
  id: 92745,
  sifraPostupka: 45135,
  nazivPonudjaca: 'Assurance payment',
  sifraPonude: 93805,
  brojPartije: 7524,
  atc: 'engage CSS',
  trazenaKolicina: 79594,
  procijenjenaVrijednost: 4950,
  nazivProizvodjaca: 'Rial Accounts yellow',
  ponudjenaVrijednost: 92542,
  vrstaPostupka: 'withdrawal',
  bodCijena: 31597,
  bodRok: 33280,
  bodUkupno: 19930,
};

export const sampleWithFullData: IPrvorangirani = {
  id: 44922,
  sifraPostupka: 1140,
  nazivPonudjaca: 'impactful Metal',
  sifraPonude: 69179,
  brojPartije: 97851,
  atc: 'Avon',
  trazenaKolicina: 98896,
  procijenjenaVrijednost: 33490,
  nazivProizvodjaca: 'invoice',
  zasticeniNaziv: 'open-source',
  jedinicnaCijena: 25462,
  ponudjenaVrijednost: 94174,
  rokIsporuke: 46593,
  vrstaPostupka: 'Administrator',
  bodCijena: 7041,
  bodRok: 43398,
  bodUkupno: 48788,
};

export const sampleWithNewData: NewPrvorangirani = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
