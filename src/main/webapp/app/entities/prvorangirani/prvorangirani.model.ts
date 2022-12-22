export interface IPrvorangirani {
  id: number;
  sifraPostupka?: number | null;
  nazivPonudjaca?: string | null;
  sifraPonude?: number | null;
  brojPartije?: number | null;
  atc?: string | null;
  trazenaKolicina?: number | null;
  procijenjenaVrijednost?: number | null;
  nazivProizvodjaca?: string | null;
  zasticeniNaziv?: string | null;
  jedinicnaCijena?: number | null;
  ponudjenaVrijednost?: number | null;
  rokIsporuke?: number | null;
  vrstaPostupka?: string | null;
  bodCijena?: number | null;
  bodRok?: number | null;
  bodUkupno?: number | null;
}

export type NewPrvorangirani = Omit<IPrvorangirani, 'id'> & { id: null };
