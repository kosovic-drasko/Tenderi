import { ITenderiHome, NewTenderiHome } from './tenderi-home.model';

export const sampleWithRequiredData: ITenderiHome = {
  id: 48220,
};

export const sampleWithPartialData: ITenderiHome = {
  id: 12470,
};

export const sampleWithFullData: ITenderiHome = {
  id: 26147,
};

export const sampleWithNewData: NewTenderiHome = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
