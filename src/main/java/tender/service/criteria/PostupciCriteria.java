package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.Postupci} entity. This class is used
 * in {@link tender.web.rest.PostupciResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /postupcis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostupciCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sifraPostupka;

    private StringFilter brojTendera;

    private StringFilter opisPostupka;

    private StringFilter vrstaPostupka;

    private LocalDateFilter datumObjave;

    private LocalDateFilter datumOtvaranja;

    private IntegerFilter kriterijumCijena;

    private IntegerFilter drugiKriterijum;

    private Boolean distinct;

    public PostupciCriteria() {}

    public PostupciCriteria(PostupciCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sifraPostupka = other.sifraPostupka == null ? null : other.sifraPostupka.copy();
        this.brojTendera = other.brojTendera == null ? null : other.brojTendera.copy();
        this.opisPostupka = other.opisPostupka == null ? null : other.opisPostupka.copy();
        this.vrstaPostupka = other.vrstaPostupka == null ? null : other.vrstaPostupka.copy();
        this.datumObjave = other.datumObjave == null ? null : other.datumObjave.copy();
        this.datumOtvaranja = other.datumOtvaranja == null ? null : other.datumOtvaranja.copy();
        this.kriterijumCijena = other.kriterijumCijena == null ? null : other.kriterijumCijena.copy();
        this.drugiKriterijum = other.drugiKriterijum == null ? null : other.drugiKriterijum.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PostupciCriteria copy() {
        return new PostupciCriteria(this);
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

    public StringFilter getBrojTendera() {
        return brojTendera;
    }

    public StringFilter brojTendera() {
        if (brojTendera == null) {
            brojTendera = new StringFilter();
        }
        return brojTendera;
    }

    public void setBrojTendera(StringFilter brojTendera) {
        this.brojTendera = brojTendera;
    }

    public StringFilter getOpisPostupka() {
        return opisPostupka;
    }

    public StringFilter opisPostupka() {
        if (opisPostupka == null) {
            opisPostupka = new StringFilter();
        }
        return opisPostupka;
    }

    public void setOpisPostupka(StringFilter opisPostupka) {
        this.opisPostupka = opisPostupka;
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

    public LocalDateFilter getDatumObjave() {
        return datumObjave;
    }

    public LocalDateFilter datumObjave() {
        if (datumObjave == null) {
            datumObjave = new LocalDateFilter();
        }
        return datumObjave;
    }

    public void setDatumObjave(LocalDateFilter datumObjave) {
        this.datumObjave = datumObjave;
    }

    public LocalDateFilter getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public LocalDateFilter datumOtvaranja() {
        if (datumOtvaranja == null) {
            datumOtvaranja = new LocalDateFilter();
        }
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(LocalDateFilter datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public IntegerFilter getKriterijumCijena() {
        return kriterijumCijena;
    }

    public IntegerFilter kriterijumCijena() {
        if (kriterijumCijena == null) {
            kriterijumCijena = new IntegerFilter();
        }
        return kriterijumCijena;
    }

    public void setKriterijumCijena(IntegerFilter kriterijumCijena) {
        this.kriterijumCijena = kriterijumCijena;
    }

    public IntegerFilter getDrugiKriterijum() {
        return drugiKriterijum;
    }

    public IntegerFilter drugiKriterijum() {
        if (drugiKriterijum == null) {
            drugiKriterijum = new IntegerFilter();
        }
        return drugiKriterijum;
    }

    public void setDrugiKriterijum(IntegerFilter drugiKriterijum) {
        this.drugiKriterijum = drugiKriterijum;
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
        final PostupciCriteria that = (PostupciCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sifraPostupka, that.sifraPostupka) &&
            Objects.equals(brojTendera, that.brojTendera) &&
            Objects.equals(opisPostupka, that.opisPostupka) &&
            Objects.equals(vrstaPostupka, that.vrstaPostupka) &&
            Objects.equals(datumObjave, that.datumObjave) &&
            Objects.equals(datumOtvaranja, that.datumOtvaranja) &&
            Objects.equals(kriterijumCijena, that.kriterijumCijena) &&
            Objects.equals(drugiKriterijum, that.drugiKriterijum) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sifraPostupka,
            brojTendera,
            opisPostupka,
            vrstaPostupka,
            datumObjave,
            datumOtvaranja,
            kriterijumCijena,
            drugiKriterijum,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostupciCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sifraPostupka != null ? "sifraPostupka=" + sifraPostupka + ", " : "") +
            (brojTendera != null ? "brojTendera=" + brojTendera + ", " : "") +
            (opisPostupka != null ? "opisPostupka=" + opisPostupka + ", " : "") +
            (vrstaPostupka != null ? "vrstaPostupka=" + vrstaPostupka + ", " : "") +
            (datumObjave != null ? "datumObjave=" + datumObjave + ", " : "") +
            (datumOtvaranja != null ? "datumOtvaranja=" + datumOtvaranja + ", " : "") +
            (kriterijumCijena != null ? "kriterijumCijena=" + kriterijumCijena + ", " : "") +
            (drugiKriterijum != null ? "drugiKriterijum=" + drugiKriterijum + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
