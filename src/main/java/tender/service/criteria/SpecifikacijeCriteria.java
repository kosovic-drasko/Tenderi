package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.Specifikacije} entity. This class is used
 * in {@link tender.web.rest.SpecifikacijeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /specifikacijes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpecifikacijeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sifraPostupka;

    private IntegerFilter brojPartije;

    private StringFilter atc;

    private StringFilter inn;

    private StringFilter farmaceutskiOblikLijeka;

    private StringFilter jacinaLijeka;

    private IntegerFilter trazenaKolicina;

    private StringFilter pakovanje;

    private StringFilter jedinicaMjere;

    private DoubleFilter procijenjenaVrijednost;

    private DoubleFilter jedinicnaCijena;

    private StringFilter karakteristika;

    private Boolean distinct;

    public SpecifikacijeCriteria() {}

    public SpecifikacijeCriteria(SpecifikacijeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sifraPostupka = other.sifraPostupka == null ? null : other.sifraPostupka.copy();
        this.brojPartije = other.brojPartije == null ? null : other.brojPartije.copy();
        this.atc = other.atc == null ? null : other.atc.copy();
        this.inn = other.inn == null ? null : other.inn.copy();
        this.farmaceutskiOblikLijeka = other.farmaceutskiOblikLijeka == null ? null : other.farmaceutskiOblikLijeka.copy();
        this.jacinaLijeka = other.jacinaLijeka == null ? null : other.jacinaLijeka.copy();
        this.trazenaKolicina = other.trazenaKolicina == null ? null : other.trazenaKolicina.copy();
        this.pakovanje = other.pakovanje == null ? null : other.pakovanje.copy();
        this.jedinicaMjere = other.jedinicaMjere == null ? null : other.jedinicaMjere.copy();
        this.procijenjenaVrijednost = other.procijenjenaVrijednost == null ? null : other.procijenjenaVrijednost.copy();
        this.jedinicnaCijena = other.jedinicnaCijena == null ? null : other.jedinicnaCijena.copy();
        this.karakteristika = other.karakteristika == null ? null : other.karakteristika.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SpecifikacijeCriteria copy() {
        return new SpecifikacijeCriteria(this);
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

    public StringFilter getJacinaLijeka() {
        return jacinaLijeka;
    }

    public StringFilter jacinaLijeka() {
        if (jacinaLijeka == null) {
            jacinaLijeka = new StringFilter();
        }
        return jacinaLijeka;
    }

    public void setJacinaLijeka(StringFilter jacinaLijeka) {
        this.jacinaLijeka = jacinaLijeka;
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

    public StringFilter getJedinicaMjere() {
        return jedinicaMjere;
    }

    public StringFilter jedinicaMjere() {
        if (jedinicaMjere == null) {
            jedinicaMjere = new StringFilter();
        }
        return jedinicaMjere;
    }

    public void setJedinicaMjere(StringFilter jedinicaMjere) {
        this.jedinicaMjere = jedinicaMjere;
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
        final SpecifikacijeCriteria that = (SpecifikacijeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sifraPostupka, that.sifraPostupka) &&
            Objects.equals(brojPartije, that.brojPartije) &&
            Objects.equals(atc, that.atc) &&
            Objects.equals(inn, that.inn) &&
            Objects.equals(farmaceutskiOblikLijeka, that.farmaceutskiOblikLijeka) &&
            Objects.equals(jacinaLijeka, that.jacinaLijeka) &&
            Objects.equals(trazenaKolicina, that.trazenaKolicina) &&
            Objects.equals(pakovanje, that.pakovanje) &&
            Objects.equals(jedinicaMjere, that.jedinicaMjere) &&
            Objects.equals(procijenjenaVrijednost, that.procijenjenaVrijednost) &&
            Objects.equals(jedinicnaCijena, that.jedinicnaCijena) &&
            Objects.equals(karakteristika, that.karakteristika) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sifraPostupka,
            brojPartije,
            atc,
            inn,
            farmaceutskiOblikLijeka,
            jacinaLijeka,
            trazenaKolicina,
            pakovanje,
            jedinicaMjere,
            procijenjenaVrijednost,
            jedinicnaCijena,
            karakteristika,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecifikacijeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sifraPostupka != null ? "sifraPostupka=" + sifraPostupka + ", " : "") +
            (brojPartije != null ? "brojPartije=" + brojPartije + ", " : "") +
            (atc != null ? "atc=" + atc + ", " : "") +
            (inn != null ? "inn=" + inn + ", " : "") +
            (farmaceutskiOblikLijeka != null ? "farmaceutskiOblikLijeka=" + farmaceutskiOblikLijeka + ", " : "") +
            (jacinaLijeka != null ? "jacinaLijeka=" + jacinaLijeka + ", " : "") +
            (trazenaKolicina != null ? "trazenaKolicina=" + trazenaKolicina + ", " : "") +
            (pakovanje != null ? "pakovanje=" + pakovanje + ", " : "") +
            (jedinicaMjere != null ? "jedinicaMjere=" + jedinicaMjere + ", " : "") +
            (procijenjenaVrijednost != null ? "procijenjenaVrijednost=" + procijenjenaVrijednost + ", " : "") +
            (jedinicnaCijena != null ? "jedinicnaCijena=" + jedinicnaCijena + ", " : "") +
            (karakteristika != null ? "karakteristika=" + karakteristika + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
