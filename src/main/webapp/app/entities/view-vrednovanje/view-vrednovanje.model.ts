export interface IViewVrednovanje {
  id: number;
  sifraPostupka?: number | null;
  sifraPonude?: number | null;
  brojPartije?: number | null;
  nazivProizvodjaca?: string | null;
  zasticeniNaziv?: string | null;
  ponudjenaVrijednost?: number | null;
  rokIsporuke?: number | null;
  jedinicnaCijena?: number | null;
  nazivPonudjaca?: string | null;
  atc?: string | null;
  trazenaKolicina?: number | null;
  procijenjenaVrijednost?: number | null;
  vrstaPostupka?: string | null;
  katekteristikaPonude?: string | null;
  karakteristikaSpecifikacije?: string | null;
  bodCijena?: number | null;
  bodRok?: number | null;
  bodUkupno?: number | null;
}

export type NewViewVrednovanje = Omit<IViewVrednovanje, 'id'> & { id: null };
