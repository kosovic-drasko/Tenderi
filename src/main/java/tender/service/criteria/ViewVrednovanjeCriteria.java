package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.ViewVrednovanje} entity. This class is used
 * in {@link tender.web.rest.ViewVrednovanjeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /view-vrednovanjes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewVrednovanjeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sifraPostupka;

    private IntegerFilter sifraPonude;

    private IntegerFilter brojPartije;

    private StringFilter nazivProizvodjaca;

    private StringFilter zasticeniNaziv;

    private IntegerFilter ponudjenaVrijednost;

    private IntegerFilter rokIsporuke;

    private DoubleFilter jedinicnaCijena;

    private StringFilter nazivPonudjaca;

    private StringFilter atc;

    private IntegerFilter trazenaKolicina;

    private DoubleFilter procijenjenaVrijednost;

    private StringFilter vrstaPostupka;

    private StringFilter katekteristikaPonude;

    private StringFilter karakteristikaSpecifikacije;

    private DoubleFilter bodCijena;

    private DoubleFilter bodRok;

    private DoubleFilter bodUkupno;

    private Boolean distinct;

    public ViewVrednovanjeCriteria() {}

    public ViewVrednovanjeCriteria(ViewVrednovanjeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sifraPostupka = other.sifraPostupka == null ? null : other.sifraPostupka.copy();
        this.sifraPonude = other.sifraPonude == null ? null : other.sifraPonude.copy();
        this.brojPartije = other.brojPartije == null ? null : other.brojPartije.copy();
        this.nazivProizvodjaca = other.nazivProizvodjaca == null ? null : other.nazivProizvodjaca.copy();
        this.zasticeniNaziv = other.zasticeniNaziv == null ? null : other.zasticeniNaziv.copy();
        this.ponudjenaVrijednost = other.ponudjenaVrijednost == null ? null : other.ponudjenaVrijednost.copy();
        this.rokIsporuke = other.rokIsporuke == null ? null : other.rokIsporuke.copy();
        this.jedinicnaCijena = other.jedinicnaCijena == null ? null : other.jedinicnaCijena.copy();
        this.nazivPonudjaca = other.nazivPonudjaca == null ? null : other.nazivPonudjaca.copy();
        this.atc = other.atc == null ? null : other.atc.copy();
        this.trazenaKolicina = other.trazenaKolicina == null ? null : other.trazenaKolicina.copy();
        this.procijenjenaVrijednost = other.procijenjenaVrijednost == null ? null : other.procijenjenaVrijednost.copy();
        this.vrstaPostupka = other.vrstaPostupka == null ? null : other.vrstaPostupka.copy();
        this.katekteristikaPonude = other.katekteristikaPonude == null ? null : other.katekteristikaPonude.copy();
        this.karakteristikaSpecifikacije = other.karakteristikaSpecifikacije == null ? null : other.karakteristikaSpecifikacije.copy();
        this.bodCijena = other.bodCijena == null ? null : other.bodCijena.copy();
        this.bodRok = other.bodRok == null ? null : other.bodRok.copy();
        this.bodUkupno = other.bodUkupno == null ? null : other.bodUkupno.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ViewVrednovanjeCriteria copy() {
        return new ViewVrednovanjeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSifraPostupka() {
        return sifraPostupka;
    }

    public IntegerFilter sifraPostupka() {
        if (sifraPostupka == null) {
            sifraPostupka = new IntegerFilter();
        }
        return sifraPostupka;
    }

    public void setSifraPostupka(IntegerFilter sifraPostupka) {
        this.sifraPostupka = sifraPostupka;
    }

    public IntegerFilter getSifraPonude() {
        return sifraPonude;
    }

    public IntegerFilter sifraPonude() {
        if (sifraPonude == null) {
            sifraPonude = new IntegerFilter();
        }
        return sifraPonude;
    }

    public void setSifraPonude(IntegerFilter sifraPonude) {
        this.sifraPonude = sifraPonude;
    }

    public IntegerFilter getBrojPartije() {
        return brojPartije;
    }

    public IntegerFilter brojPartije() {
        if (brojPartije == null) {
            brojPartije = new IntegerFilter();
        }
        return brojPartije;
    }

    public void setBrojPartije(IntegerFilter brojPartije) {
        this.brojPartije = brojPartije;
    }

    public StringFilter getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public StringFilter nazivProizvodjaca() {
        if (nazivProizvodjaca == null) {
            nazivProizvodjaca = new StringFilter();
        }
        return nazivProizvodjaca;
    }

    public void setNazivProizvodjaca(StringFilter nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
    }

    public StringFilter getZasticeniNaziv() {
        return zasticeniNaziv;
    }

    public StringFilter zasticeniNaziv() {
        if (zasticeniNaziv == null) {
            zasticeniNaziv = new StringFilter();
        }
        return zasticeniNaziv;
    }

    public void setZasticeniNaziv(StringFilter zasticeniNaziv) {
        this.zasticeniNaziv = zasticeniNaziv;
    }

    public IntegerFilter getPonudjenaVrijednost() {
        return ponudjenaVrijednost;
    }

    public IntegerFilter ponudjenaVrijednost() {
        if (ponudjenaVrijednost == null) {
            ponudjenaVrijednost = new IntegerFilter();
        }
        return ponudjenaVrijednost;
    }

    public void setPonudjenaVrijednost(IntegerFilter ponudjenaVrijednost) {
        this.ponudjenaVrijednost = ponudjenaVrijednost;
    }

    public IntegerFilter getRokIsporuke() {
        return rokIsporuke;
    }

    public IntegerFilter rokIsporuke() {
        if (rokIsporuke == null) {
            rokIsporuke = new IntegerFilter();
        }
        return rokIsporuke;
    }

    public void setRokIsporuke(IntegerFilter rokIsporuke) {
        this.rokIsporuke = rokIsporuke;
    }

    public DoubleFilter getJedinicnaCijena() {
        return jedinicnaCijena;
    }

    public DoubleFilter jedinicnaCijena() {
        if (jedinicnaCijena == null) {
            jedinicnaCijena = new DoubleFilter();
        }
        return jedinicnaCijena;
    }

    public void setJedinicnaCijena(DoubleFilter jedinicnaCijena) {
        this.jedinicnaCijena = jedinicnaCijena;
    }

    public StringFilter getNazivPonudjaca() {
        return nazivPonudjaca;
    }

    public StringFilter nazivPonudjaca() {
        if (nazivPonudjaca == null) {
            nazivPonudjaca = new StringFilter();
        }
        return nazivPonudjaca;
    }

    public void setNazivPonudjaca(StringFilter nazivPonudjaca) {
        this.nazivPonudjaca = nazivPonudjaca;
    }

    public StringFilter getAtc() {
        return atc;
    }

    public StringFilter atc() {
        if (atc == null) {
            atc = new StringFilter();
        }
        return atc;
    }

    public void setAtc(StringFilter atc) {
        this.atc = atc;
    }

    public IntegerFilter getTrazenaKolicina() {
        return trazenaKolicina;
    }

    public IntegerFilter trazenaKolicina() {
        if (trazenaKolicina == null) {
            trazenaKolicina = new IntegerFilter();
        }
        return trazenaKolicina;
    }

    public void setTrazenaKolicina(IntegerFilter trazenaKolicina) {
        this.trazenaKolicina = trazenaKolicina;
    }

    public DoubleFilter getProcijenjenaVrijednost() {
        return procijenjenaVrijednost;
    }

    public DoubleFilter procijenjenaVrijednost() {
        if (procijenjenaVrijednost == null) {
            procijenjenaVrijednost = new DoubleFilter();
        }
        return procijenjenaVrijednost;
    }

    public void setProcijenjenaVrijednost(DoubleFilter procijenjenaVrijednost) {
        this.procijenjenaVrijednost = procijenjenaVrijednost;
    }

    public StringFilter getVrstaPostupka() {
        return vrstaPostupka;
    }

    public StringFilter vrstaPostupka() {
        if (vrstaPostupka == null) {
            vrstaPostupka = new StringFilter();
        }
        return vrstaPostupka;
    }

    public void setVrstaPostupka(StringFilter vrstaPostupka) {
        this.vrstaPostupka = vrstaPostupka;
    }

    public StringFilter getKatekteristikaPonude() {
        return katekteristikaPonude;
    }

    public StringFilter katekteristikaPonude() {
        if (katekteristikaPonude == null) {
            katekteristikaPonude = new StringFilter();
        }
        return katekteristikaPonude;
    }

    public void setKatekteristikaPonude(StringFilter katekteristikaPonude) {
        this.katekteristikaPonude = katekteristikaPonude;
    }

    public StringFilter getKarakteristikaSpecifikacije() {
        return karakteristikaSpecifikacije;
    }

    public StringFilter karakteristikaSpecifikacije() {
        if (karakteristikaSpecifikacije == null) {
            karakteristikaSpecifikacije = new StringFilter();
        }
        return karakteristikaSpecifikacije;
    }

    public void setKarakteristikaSpecifikacije(StringFilter karakteristikaSpecifikacije) {
        this.karakteristikaSpecifikacije = karakteristikaSpecifikacije;
    }

    public DoubleFilter getBodCijena() {
        return bodCijena;
    }

    public DoubleFilter bodCijena() {
        if (bodCijena == null) {
            bodCijena = new DoubleFilter();
        }
        return bodCijena;
    }

    public void setBodCijena(DoubleFilter bodCijena) {
        this.bodCijena = bodCijena;
    }

    public DoubleFilter getBodRok() {
        return bodRok;
    }

    public DoubleFilter bodRok() {
        if (bodRok == null) {
            bodRok = new DoubleFilter();
        }
        return bodRok;
    }

    public void setBodRok(DoubleFilter bodRok) {
        this.bodRok = bodRok;
    }

    public DoubleFilter getBodUkupno() {
        return bodUkupno;
    }

    public DoubleFilter bodUkupno() {
        if (bodUkupno == null) {
            bodUkupno = new DoubleFilter();
        }
        return bodUkupno;
    }

    public void setBodUkupno(DoubleFilter bodUkupno) {
        this.bodUkupno = bodUkupno;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ViewVrednovanjeCriteria that = (ViewVrednovanjeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sifraPostupka, that.sifraPostupka) &&
            Objects.equals(sifraPonude, that.sifraPonude) &&
            Objects.equals(brojPartije, that.brojPartije) &&
            Objects.equals(nazivProizvodjaca, that.nazivProizvodjaca) &&
            Objects.equals(zasticeniNaziv, that.zasticeniNaziv) &&
            Objects.equals(ponudjenaVrijednost, that.ponudjenaVrijednost) &&
            Objects.equals(rokIsporuke, that.rokIsporuke) &&
            Objects.equals(jedinicnaCijena, that.jedinicnaCijena) &&
            Objects.equals(nazivPonudjaca, that.nazivPonudjaca) &&
            Objects.equals(atc, that.atc) &&
            Objects.equals(trazenaKolicina, that.trazenaKolicina) &&
            Objects.equals(procijenjenaVrijednost, that.procijenjenaVrijednost) &&
            Objects.equals(vrstaPostupka, that.vrstaPostupka) &&
            Objects.equals(katekteristikaPonude, that.katekteristikaPonude) &&
            Objects.equals(karakteristikaSpecifikacije, that.karakteristikaSpecifikacije) &&
            Objects.equals(bodCijena, that.bodCijena) &&
            Objects.equals(bodRok, that.bodRok) &&
            Objects.equals(bodUkupno, that.bodUkupno) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sifraPostupka,
            sifraPonude,
            brojPartije,
            nazivProizvodjaca,
            zasticeniNaziv,
            ponudjenaVrijednost,
            rokIsporuke,
            jedinicnaCijena,
            nazivPonudjaca,
            atc,
            trazenaKolicina,
            procijenjenaVrijednost,
            vrstaPostupka,
            katekteristikaPonude,
            karakteristikaSpecifikacije,
            bodCijena,
            bodRok,
            bodUkupno,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewVrednovanjeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sifraPostupka != null ? "sifraPostupka=" + sifraPostupka + ", " : "") +
            (sifraPonude != null ? "sifraPonude=" + sifraPonude + ", " : "") +
            (brojPartije != null ? "brojPartije=" + brojPartije + ", " : "") +
            (nazivProizvodjaca != null ? "nazivProizvodjaca=" + nazivProizvodjaca + ", " : "") +
            (zasticeniNaziv != null ? "zasticeniNaziv=" + zasticeniNaziv + ", " : "") +
            (ponudjenaVrijednost != null ? "ponudjenaVrijednost=" + ponudjenaVrijednost + ", " : "") +
            (rokIsporuke != null ? "rokIsporuke=" + rokIsporuke + ", " : "") +
            (jedinicnaCijena != null ? "jedinicnaCijena=" + jedinicnaCijena + ", " : "") +
            (nazivPonudjaca != null ? "nazivPonudjaca=" + nazivPonudjaca + ", " : "") +
            (atc != null ? "atc=" + atc + ", " : "") +
            (trazenaKolicina != null ? "trazenaKolicina=" + trazenaKolicina + ", " : "") +
            (procijenjenaVrijednost != null ? "procijenjenaVrijednost=" + procijenjenaVrijednost + ", " : "") +
            (vrstaPostupka != null ? "vrstaPostupka=" + vrstaPostupka + ", " : "") +
            (katekteristikaPonude != null ? "katekteristikaPonude=" + katekteristikaPonude + ", " : "") +
            (karakteristikaSpecifikacije != null ? "karakteristikaSpecifikacije=" + karakteristikaSpecifikacije + ", " : "") +
            (bodCijena != null ? "bodCijena=" + bodCijena + ", " : "") +
            (bodRok != null ? "bodRok=" + bodRok + ", " : "") +
            (bodUkupno != null ? "bodUkupno=" + bodUkupno + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
