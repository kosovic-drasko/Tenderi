package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.HvalePonude} entity. This class is used
 * in {@link tender.web.rest.HvalePonudeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hvale-ponudes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HvalePonudeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sifraPostupka;

    private IntegerFilter brojPartije;

    private StringFilter inn;

    private StringFilter farmaceutskiOblikLijeka;

    private StringFilter pakovanje;

    private IntegerFilter trazenaKolicina;

    private DoubleFilter procijenjenaVrijednost;

    private Boolean distinct;

    public HvalePonudeCriteria() {}

    public HvalePonudeCriteria(HvalePonudeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sifraPostupka = other.sifraPostupka == null ? null : other.sifraPostupka.copy();
        this.brojPartije = other.brojPartije == null ? null : other.brojPartije.copy();
        this.inn = other.inn == null ? null : other.inn.copy();
        this.farmaceutskiOblikLijeka = other.farmaceutskiOblikLijeka == null ? null : other.farmaceutskiOblikLijeka.copy();
        this.pakovanje = other.pakovanje == null ? null : other.pakovanje.copy();
        this.trazenaKolicina = other.trazenaKolicina == null ? null : other.trazenaKolicina.copy();
        this.procijenjenaVrijednost = other.procijenjenaVrijednost == null ? null : other.procijenjenaVrijednost.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HvalePonudeCriteria copy() {
        return new HvalePonudeCriteria(this);
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

    public StringFilter getInn() {
        return inn;
    }

    public StringFilter inn() {
        if (inn == null) {
            inn = new StringFilter();
        }
        return inn;
    }

    public void setInn(StringFilter inn) {
        this.inn = inn;
    }

    public StringFilter getFarmaceutskiOblikLijeka() {
        return farmaceutskiOblikLijeka;
    }

    public StringFilter farmaceutskiOblikLijeka() {
        if (farmaceutskiOblikLijeka == null) {
            farmaceutskiOblikLijeka = new StringFilter();
        }
        return farmaceutskiOblikLijeka;
    }

    public void setFarmaceutskiOblikLijeka(StringFilter farmaceutskiOblikLijeka) {
        this.farmaceutskiOblikLijeka = farmaceutskiOblikLijeka;
    }

    public StringFilter getPakovanje() {
        return pakovanje;
    }

    public StringFilter pakovanje() {
        if (pakovanje == null) {
            pakovanje = new StringFilter();
        }
        return pakovanje;
    }

    public void setPakovanje(StringFilter pakovanje) {
        this.pakovanje = pakovanje;
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
        final HvalePonudeCriteria that = (HvalePonudeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sifraPostupka, that.sifraPostupka) &&
            Objects.equals(brojPartije, that.brojPartije) &&
            Objects.equals(inn, that.inn) &&
            Objects.equals(farmaceutskiOblikLijeka, that.farmaceutskiOblikLijeka) &&
            Objects.equals(pakovanje, that.pakovanje) &&
            Objects.equals(trazenaKolicina, that.trazenaKolicina) &&
            Objects.equals(procijenjenaVrijednost, that.procijenjenaVrijednost) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sifraPostupka,
            brojPartije,
            inn,
            farmaceutskiOblikLijeka,
            pakovanje,
            trazenaKolicina,
            procijenjenaVrijednost,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HvalePonudeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sifraPostupka != null ? "sifraPostupka=" + sifraPostupka + ", " : "") +
            (brojPartije != null ? "brojPartije=" + brojPartije + ", " : "") +
            (inn != null ? "inn=" + inn + ", " : "") +
            (farmaceutskiOblikLijeka != null ? "farmaceutskiOblikLijeka=" + farmaceutskiOblikLijeka + ", " : "") +
            (pakovanje != null ? "pakovanje=" + pakovanje + ", " : "") +
            (trazenaKolicina != null ? "trazenaKolicina=" + trazenaKolicina + ", " : "") +
            (procijenjenaVrijednost != null ? "procijenjenaVrijednost=" + procijenjenaVrijednost + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
