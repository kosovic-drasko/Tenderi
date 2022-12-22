import dayjs from 'dayjs/esm';

export interface IPostupci {
  id: number;
  sifraPostupka?: number | null;
  brojTendera?: string | null;
  opisPostupka?: string | null;
  vrstaPostupka?: string | null;
  datumObjave?: dayjs.Dayjs | null;
  datumOtvaranja?: dayjs.Dayjs | null;
  kriterijumCijena?: number | null;
  drugiKriterijum?: number | null;
}

export type NewPostupci = Omit<IPostupci, 'id'> & { id: null };
