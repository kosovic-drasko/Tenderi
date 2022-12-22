package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.ViewPonude} entity. This class is used
 * in {@link tender.web.rest.ViewPonudeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /view-ponudes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewPonudeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sifraPostupka;

    private IntegerFilter sifraPonude;

    private IntegerFilter brojPartije;

    private StringFilter nazivProizvodjaca;

    private StringFilter zasticeniNaziv;

    private DoubleFilter ponudjenaVrijednost;

    private IntegerFilter rokIsporuke;

    private DoubleFilter jedinicnaCijena;

    private BooleanFilter selected;

    private StringFilter nazivPonudjaca;

    private StringFilter karakteristika;

    private Boolean distinct;

    public ViewPonudeCriteria() {}

    public ViewPonudeCriteria(ViewPonudeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sifraPostupka = other.sifraPostupka == null ? null : other.sifraPostupka.copy();
        this.sifraPonude = other.sifraPonude == null ? null : other.sifraPonude.copy();
        this.brojPartije = other.brojPartije == null ? null : other.brojPartije.copy();
        this.nazivProizvodjaca = other.nazivProizvodjaca == null ? null : other.nazivProizvodjaca.copy();
        this.zasticeniNaziv = other.zasticeniNaziv == null ? null : other.zasticeniNaziv.copy();
        this.ponudjenaVrijednost = other.ponudjenaVrijednost == null ? null : other.ponudjenaVrijednost.copy();
        this.rokIsporuke = other.rokIsporuke == null ? null : other.rokIsporuke.copy();
        this.jedinicnaCijena = other.jedinicnaCijena == null ? null : other.jedinicnaCijena.copy();
        this.selected = other.selected == null ? null : other.selected.copy();
        this.nazivPonudjaca = other.nazivPonudjaca == null ? null : other.nazivPonudjaca.copy();
        this.karakteristika = other.karakteristika == null ? null : other.karakteristika.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ViewPonudeCriteria copy() {
        return new ViewPonudeCriteria(this);
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

    public BooleanFilter getSelected() {
        return selected;
    }

    public BooleanFilter selected() {
        if (selected == null) {
            selected = new BooleanFilter();
        }
        return selected;
    }

    public void setSelected(BooleanFilter selected) {
        this.selected = selected;
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

    public StringFilter getKarakteristika() {
        return karakteristika;
    }

    public StringFilter karakteristika() {
        if (karakteristika == null) {
            karakteristika = new StringFilter();
        }
        return karakteristika;
    }

    public void setKarakteristika(StringFilter karakteristika) {
        this.karakteristika = karakteristika;
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
        final ViewPonudeCriteria that = (ViewPonudeCriteria) o;
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
            Objects.equals(selected, that.selected) &&
            Objects.equals(nazivPonudjaca, that.nazivPonudjaca) &&
            Objects.equals(karakteristika, that.karakteristika) &&
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
            selected,
            nazivPonudjaca,
            karakteristika,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewPonudeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sifraPostupka != null ? "sifraPostupka=" + sifraPostupka + ", " : "") +
            (sifraPonude != null ? "sifraPonude=" + sifraPonude + ", " : "") +
            (brojPartije != null ? "brojPartije=" + brojPartije + ", " : "") +
            (nazivProizvodjaca != null ? "nazivProizvodjaca=" + nazivProizvodjaca + ", " : "") +
            (zasticeniNaziv != null ? "zasticeniNaziv=" + zasticeniNaziv + ", " : "") +
            (ponudjenaVrijednost != null ? "ponudjenaVrijednost=" + ponudjenaVrijednost + ", " : "") +
            (rokIsporuke != null ? "rokIsporuke=" + rokIsporuke + ", " : "") +
            (jedinicnaCijena != null ? "jedinicnaCijena=" + jedinicnaCijena + ", " : "") +
            (selected != null ? "selected=" + selected + ", " : "") +
            (nazivPonudjaca != null ? "nazivPonudjaca=" + nazivPonudjaca + ", " : "") +
            (karakteristika != null ? "karakteristika=" + karakteristika + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
