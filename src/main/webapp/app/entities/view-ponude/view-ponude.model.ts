export interface IViewPonude {
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
  nazivPonudjaca?: string | null;
  karakteristika?: string | null;
}

export type NewViewPonude = Omit<IViewPonude, 'id'> & { id: null };
