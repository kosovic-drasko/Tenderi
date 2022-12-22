import dayjs from 'dayjs/esm';

import { IPostupci, NewPostupci } from './postupci.model';

export const sampleWithRequiredData: IPostupci = {
  id: 50728,
  sifraPostupka: 86044,
  opisPostupka: 'transmitter',
  vrstaPostupka: 'matrix',
  kriterijumCijena: 47895,
  drugiKriterijum: 90906,
};

export const sampleWithPartialData: IPostupci = {
  id: 41361,
  sifraPostupka: 54637,
  opisPostupka: 'Web Fantastic',
  vrstaPostupka: 'withdrawal',
  datumOtvaranja: dayjs('2022-08-13'),
  kriterijumCijena: 74042,
  drugiKriterijum: 90097,
};

export const sampleWithFullData: IPostupci = {
  id: 62313,
  sifraPostupka: 40559,
  brojTendera: 'America Flat',
  opisPostupka: 'reboot Gorgeous',
  vrstaPostupka: 'TCP',
  datumObjave: dayjs('2022-08-13'),
  datumOtvaranja: dayjs('2022-08-12'),
  kriterijumCijena: 21725,
  drugiKriterijum: 42132,
};

export const sampleWithNewData: NewPostupci = {
  sifraPostupka: 66453,
  opisPostupka: 'redundant',
  vrstaPostupka: 'Lari synthesizing Pizza',
  kriterijumCijena: 65373,
  drugiKriterijum: 3480,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
