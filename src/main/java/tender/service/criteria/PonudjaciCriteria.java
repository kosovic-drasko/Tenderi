package tender.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link tender.domain.Ponudjaci} entity. This class is used
 * in {@link tender.web.rest.PonudjaciResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ponudjacis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PonudjaciCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nazivPonudjaca;

    private StringFilter odgovornoLice;

    private StringFilter adresaPonudjaca;

    private StringFilter bankaRacun;

    private Boolean distinct;

    public PonudjaciCriteria() {}

    public PonudjaciCriteria(PonudjaciCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nazivPonudjaca = other.nazivPonudjaca == null ? null : other.nazivPonudjaca.copy();
        this.odgovornoLice = other.odgovornoLice == null ? null : other.odgovornoLice.copy();
        this.adresaPonudjaca = other.adresaPonudjaca == null ? null : other.adresaPonudjaca.copy();
        this.bankaRacun = other.bankaRacun == null ? null : other.bankaRacun.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PonudjaciCriteria copy() {
        return new PonudjaciCriteria(this);
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

    public StringFilter getOdgovornoLice() {
        return odgovornoLice;
    }

    public StringFilter odgovornoLice() {
        if (odgovornoLice == null) {
            odgovornoLice = new StringFilter();
        }
        return odgovornoLice;
    }

    public void setOdgovornoLice(StringFilter odgovornoLice) {
        this.odgovornoLice = odgovornoLice;
    }

    public StringFilter getAdresaPonudjaca() {
        return adresaPonudjaca;
    }

    public StringFilter adresaPonudjaca() {
        if (adresaPonudjaca == null) {
            adresaPonudjaca = new StringFilter();
        }
        return adresaPonudjaca;
    }

    public void setAdresaPonudjaca(StringFilter adresaPonudjaca) {
        this.adresaPonudjaca = adresaPonudjaca;
    }

    public StringFilter getBankaRacun() {
        return bankaRacun;
    }

    public StringFilter bankaRacun() {
        if (bankaRacun == null) {
            bankaRacun = new StringFilter();
        }
        return bankaRacun;
    }

    public void setBankaRacun(StringFilter bankaRacun) {
        this.bankaRacun = bankaRacun;
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
        final PonudjaciCriteria that = (PonudjaciCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nazivPonudjaca, that.nazivPonudjaca) &&
            Objects.equals(odgovornoLice, that.odgovornoLice) &&
            Objects.equals(adresaPonudjaca, that.adresaPonudjaca) &&
            Objects.equals(bankaRacun, that.bankaRacun) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nazivPonudjaca, odgovornoLice, adresaPonudjaca, bankaRacun, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PonudjaciCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nazivPonudjaca != null ? "nazivPonudjaca=" + nazivPonudjaca + ", " : "") +
            (odgovornoLice != null ? "odgovornoLice=" + odgovornoLice + ", " : "") +
            (adresaPonudjaca != null ? "adresaPonudjaca=" + adresaPonudjaca + ", " : "") +
            (bankaRacun != null ? "bankaRacun=" + bankaRacun + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
