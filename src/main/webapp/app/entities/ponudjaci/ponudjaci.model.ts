export interface IPonudjaci {
  id: number;
  nazivPonudjaca?: string | null;
  odgovornoLice?: string | null;
  adresaPonudjaca?: string | null;
  bankaRacun?: string | null;
}

export type NewPonudjaci = Omit<IPonudjaci, 'id'> & { id: null };
