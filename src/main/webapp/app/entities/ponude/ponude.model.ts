import { IPonudjaci } from 'app/entities/ponudjaci/ponudjaci.model';

export interface IPonude {
  id?: number;
  sifraPostupka?: number;
  sifraPonude?: number;
  brojPartije?: number;
  nazivProizvodjaca?: string | null;
  zasticeniNaziv?: string | null;
  karakteristika?: string | null;
  ponudjenaVrijednost?: number;
  rokIsporuke?: number;
  jedinicnaCijena?: number | null;
  selected?: boolean | null;
  sifraPonudjaca?: number | null;
  ponudjaci?: IPonudjaci | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: string | null;
}

export class Ponude implements IPonude {
  constructor(
    public id?: number,
    public sifraPostupka?: number,
    public sifraPonude?: number,
    public brojPartije?: number,
    public nazivProizvodjaca?: string | null,
    public zasticeniNaziv?: string | null,
    public karakteristika?: string | null,
    public ponudjenaVrijednost?: number,
    public rokIsporuke?: number,
    public jedinicnaCijena?: number | null,
    public sifraPonudjaca?: number | null,
    public ponudjaci?: IPonudjaci | null,
    public selected?: boolean | null,
    public createdBy?: string | null,
    public createdDate?: Date | null,
    public lastModifiedBy?: string | null
  ) {
    this.selected = this.selected ?? false;
  }
}
