export interface IHvalePonude {
  id: number;
  sifraPostupka?: number | null;
  brojPartije?: number | null;
  inn?: string | null;
  farmaceutskiOblikLijeka?: string | null;
  pakovanje?: string | null;
  trazenaKolicina?: number | null;
  procijenjenaVrijednost?: number | null;
}

export type NewHvalePonude = Omit<IHvalePonude, 'id'> & { id: null };
