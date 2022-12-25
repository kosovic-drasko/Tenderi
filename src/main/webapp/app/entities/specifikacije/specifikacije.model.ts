export interface ISpecifikacije {
  id?: number;
  sifraPostupka?: number;
  brojPartije?: number;
  atc?: string | null;
  inn?: string | null;
  farmaceutskiOblikLijeka?: string | null;
  karakteristika?: string | null;
  jacinaLijeka?: string | null;
  trazenaKolicina?: number | null;
  pakovanje?: string | null;
  jedinicaMjere?: string | null;
  procijenjenaVrijednost?: number;
  jedinicnaCijena?: number;
}

export class Specifikacije implements ISpecifikacije {
  constructor(
    public id?: number,
    public sifraPostupka?: number,
    public brojPartije?: number,
    public atc?: string | null,
    public inn?: string | null,
    public farmaceutskiOblikLijeka?: string | null,
    public karakteristika?: string | null,
    public jacinaLijeka?: string | null,
    public trazenaKolicina?: number | null,
    public pakovanje?: string | null,
    public jedinicaMjere?: string | null,
    public procijenjenaVrijednost?: number,
    public jedinicnaCijena?: number
  ) {}
}

export function getSpecifikacijeIdentifier(specifikacije: ISpecifikacije): number | undefined {
  return specifikacije.id;
}
