export interface ISpecifikacije {
  id: number;
  sifraPostupka?: number | null;
  brojPartije?: number | null;
  atc?: string | null;
  inn?: string | null;
  farmaceutskiOblikLijeka?: string | null;
  jacinaLijeka?: string | null;
  trazenaKolicina?: number | null;
  pakovanje?: string | null;
  jedinicaMjere?: string | null;
  procijenjenaVrijednost?: number | null;
  jedinicnaCijena?: number | null;
  karakteristika?: string | null;
}

export type NewSpecifikacije = Omit<ISpecifikacije, 'id'> & { id: null };
