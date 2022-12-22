export interface IVrednovanje {
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
  bodCijena?: number | null;
  bodRok?: number | null;
  bodUkupno?: number | null;
}

export type NewVrednovanje = Omit<IVrednovanje, 'id'> & { id: null };
