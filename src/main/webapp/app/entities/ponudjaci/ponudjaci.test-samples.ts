import { IPonudjaci, NewPonudjaci } from './ponudjaci.model';

export const sampleWithRequiredData: IPonudjaci = {
  id: 58649,
};

export const sampleWithPartialData: IPonudjaci = {
  id: 65119,
  adresaPonudjaca: 'leading-edge',
  bankaRacun: 'Monaco compressing utilize',
};

export const sampleWithFullData: IPonudjaci = {
  id: 35117,
  nazivPonudjaca: 'next-generation back',
  odgovornoLice: 'Senior',
  adresaPonudjaca: 'complexity Berkshire',
  bankaRacun: 'Planner',
};

export const sampleWithNewData: NewPonudjaci = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
