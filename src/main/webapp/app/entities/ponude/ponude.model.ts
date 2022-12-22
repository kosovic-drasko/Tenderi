export interface IPonude {
  id: number;
  sifraPostupka?: number | null;
  sifraPonude?: number | null;
  brojPartije?: number | null;
  nazivProizvodjaca?: string | null;
  zasticeniNaziv?: string | null;
  ponudjenaVrijednost?: number | null;
  rokIsporuke?: number | null;
  jedinicnaCijena?: number | null;
  selected?: boolean | null;
  sifraPonudjaca?: number | null;
  karakteristika?: string | null;
}

export type NewPonude = Omit<IPonude, 'id'> & { id: null };
