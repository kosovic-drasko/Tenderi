package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.ViewPrvorangirani} entity. This class is used
 * in {@link tender.web.rest.ViewPrvorangiraniResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /view-prvorangiranis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewPrvorangiraniCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sifraPostupka;

    private StringFilter nazivPonudjaca;

    private IntegerFilter sifraPonude;

    private StringFilter atc;

    private IntegerFilter trazenaKolicina;

    private DoubleFilter procijenjenaVrijednost;

    private StringFilter nazivProizvodjaca;

    private StringFilter zasticeniNaziv;

    private DoubleFilter jedinicnaCijena;

    private DoubleFilter ponudjenaVrijednost;

    private IntegerFilter rokIsporuke;

    private StringFilter vrstaPostupka;

    private DoubleFilter bodCijena;

    private DoubleFilter bodRok;

    private DoubleFilter bodUkupno;

    private StringFilter karakteristikaSpecifikacije;

    private StringFilter karakteristikaPonude;

    private Boolean distinct;

    public ViewPrvorangiraniCriteria() {}

    public ViewPrvorangiraniCriteria(ViewPrvorangiraniCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sifraPostupka = other.sifraPostupka == null ? null : other.sifraPostupka.copy();
        this.nazivPonudjaca = other.nazivPonudjaca == null ? null : other.nazivPonudjaca.copy();
        this.sifraPonude = other.sifraPonude == null ? null : other.sifraPonude.copy();
        this.atc = other.atc == null ? null : other.atc.copy();
        this.trazenaKolicina = other.trazenaKolicina == null ? null : other.trazenaKolicina.copy();
        this.procijenjenaVrijednost = other.procijenjenaVrijednost == null ? null : other.procijenjenaVrijednost.copy();
        this.nazivProizvodjaca = other.nazivProizvodjaca == null ? null : other.nazivProizvodjaca.copy();
        this.zasticeniNaziv = other.zasticeniNaziv == null ? null : other.zasticeniNaziv.copy();
        this.jedinicnaCijena = other.jedinicnaCijena == null ? null : other.jedinicnaCijena.copy();
        this.ponudjenaVrijednost = other.ponudjenaVrijednost == null ? null : other.ponudjenaVrijednost.copy();
        this.rokIsporuke = other.rokIsporuke == null ? null : other.rokIsporuke.copy();
        this.vrstaPostupka = other.vrstaPostupka == null ? null : other.vrstaPostupka.copy();
        this.bodCijena = other.bodCijena == null ? null : other.bodCijena.copy();
        this.bodRok = other.bodRok == null ? null : other.bodRok.copy();
        this.bodUkupno = other.bodUkupno == null ? null : other.bodUkupno.copy();
        this.karakteristikaSpecifikacije = other.karakteristikaSpecifikacije == null ? null : other.karakteristikaSpecifikacije.copy();
        this.karakteristikaPonude = other.karakteristikaPonude == null ? null : other.karakteristikaPonude.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ViewPrvorangiraniCriteria copy() {
        return new ViewPrvorangiraniCriteria(this);
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

    public DoubleFilter getPonudjenaVrijednost() {
        return ponudjenaVrijednost;
    }

    public DoubleFilter ponudjenaVrijednost() {
        if (ponudjenaVrijednost == null) {
            ponudjenaVrijednost = new DoubleFilter();
        }
        return ponudjenaVrijednost;
    }

    public void setPonudjenaVrijednost(DoubleFilter ponudjenaVrijednost) {
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

    public StringFilter getKarakteristikaPonude() {
        return karakteristikaPonude;
    }

    public StringFilter karakteristikaPonude() {
        if (karakteristikaPonude == null) {
            karakteristikaPonude = new StringFilter();
        }
        return karakteristikaPonude;
    }

    public void setKarakteristikaPonude(StringFilter karakteristikaPonude) {
        this.karakteristikaPonude = karakteristikaPonude;
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
        final ViewPrvorangiraniCriteria that = (ViewPrvorangiraniCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sifraPostupka, that.sifraPostupka) &&
            Objects.equals(nazivPonudjaca, that.nazivPonudjaca) &&
            Objects.equals(sifraPonude, that.sifraPonude) &&
            Objects.equals(atc, that.atc) &&
            Objects.equals(trazenaKolicina, that.trazenaKolicina) &&
            Objects.equals(procijenjenaVrijednost, that.procijenjenaVrijednost) &&
            Objects.equals(nazivProizvodjaca, that.nazivProizvodjaca) &&
            Objects.equals(zasticeniNaziv, that.zasticeniNaziv) &&
            Objects.equals(jedinicnaCijena, that.jedinicnaCijena) &&
            Objects.equals(ponudjenaVrijednost, that.ponudjenaVrijednost) &&
            Objects.equals(rokIsporuke, that.rokIsporuke) &&
            Objects.equals(vrstaPostupka, that.vrstaPostupka) &&
            Objects.equals(bodCijena, that.bodCijena) &&
            Objects.equals(bodRok, that.bodRok) &&
            Objects.equals(bodUkupno, that.bodUkupno) &&
            Objects.equals(karakteristikaSpecifikacije, that.karakteristikaSpecifikacije) &&
            Objects.equals(karakteristikaPonude, that.karakteristikaPonude) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sifraPostupka,
            nazivPonudjaca,
            sifraPonude,
            atc,
            trazenaKolicina,
            procijenjenaVrijednost,
            nazivProizvodjaca,
            zasticeniNaziv,
            jedinicnaCijena,
            ponudjenaVrijednost,
            rokIsporuke,
            vrstaPostupka,
            bodCijena,
            bodRok,
            bodUkupno,
            karakteristikaSpecifikacije,
            karakteristikaPonude,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewPrvorangiraniCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sifraPostupka != null ? "sifraPostupka=" + sifraPostupka + ", " : "") +
            (nazivPonudjaca != null ? "nazivPonudjaca=" + nazivPonudjaca + ", " : "") +
            (sifraPonude != null ? "sifraPonude=" + sifraPonude + ", " : "") +
            (atc != null ? "atc=" + atc + ", " : "") +
            (trazenaKolicina != null ? "trazenaKolicina=" + trazenaKolicina + ", " : "") +
            (procijenjenaVrijednost != null ? "procijenjenaVrijednost=" + procijenjenaVrijednost + ", " : "") +
            (nazivProizvodjaca != null ? "nazivProizvodjaca=" + nazivProizvodjaca + ", " : "") +
            (zasticeniNaziv != null ? "zasticeniNaziv=" + zasticeniNaziv + ", " : "") +
            (jedinicnaCijena != null ? "jedinicnaCijena=" + jedinicnaCijena + ", " : "") +
            (ponudjenaVrijednost != null ? "ponudjenaVrijednost=" + ponudjenaVrijednost + ", " : "") +
            (rokIsporuke != null ? "rokIsporuke=" + rokIsporuke + ", " : "") +
            (vrstaPostupka != null ? "vrstaPostupka=" + vrstaPostupka + ", " : "") +
            (bodCijena != null ? "bodCijena=" + bodCijena + ", " : "") +
            (bodRok != null ? "bodRok=" + bodRok + ", " : "") +
            (bodUkupno != null ? "bodUkupno=" + bodUkupno + ", " : "") +
            (karakteristikaSpecifikacije != null ? "karakteristikaSpecifikacije=" + karakteristikaSpecifikacije + ", " : "") +
            (karakteristikaPonude != null ? "karakteristikaPonude=" + karakteristikaPonude + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
