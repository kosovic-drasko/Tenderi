export interface IVrednovanje {
  id?: number;
  sifraPostupka?: number | null;
  sifraPonude?: number | null;
  brojPartije?: number | null;
  nazivProizvodjaca?: string | null;
  zasticeniNaziv?: string | null;
  karakteristikaPonude?: string | null;
  karakteristikaSpecifikacije?: string | null;
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

export class Vrednovanje implements IVrednovanje {
  constructor(
    public id?: number,
    public sifraPostupka?: number | null,
    public sifraPonude?: number | null,
    public brojPartije?: number | null,
    public nazivProizvodjaca?: string | null,
    public zasticeniNaziv?: string | null,
    public karakteristikaPonude?: string | null,
    public karakteristikaSpecifikacije?: string | null,
    public ponudjenaVrijednost?: number | null,
    public rokIsporuke?: number | null,
    public jedinicnaCijena?: number | null,
    public nazivPonudjaca?: string | null,
    public atc?: string | null,
    public trazenaKolicina?: number | null,
    public procijenjenaVrijednost?: number | null,
    public vrstaPostupka?: string | null,
    public bodCijena?: number | null,
    public bodRok?: number | null,
    public bodUkupno?: number | null
  ) {}
}

export function getVrednovanjeIdentifier(vrednovanje: IVrednovanje): number | undefined {
  return vrednovanje.id;
}
