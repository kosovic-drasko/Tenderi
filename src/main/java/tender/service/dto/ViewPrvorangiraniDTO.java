package tender.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link tender.domain.ViewPrvorangirani} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewPrvorangiraniDTO implements Serializable {

    private Long id;

    private Integer sifraPostupka;

    private String nazivPonudjaca;

    private Integer sifraPonude;

    private String atc;

    private Integer trazenaKolicina;

    private Double procijenjenaVrijednost;

    private String nazivProizvodjaca;

    private String zasticeniNaziv;

    private Double jedinicnaCijena;

    private Double ponudjenaVrijednost;

    private Integer rokIsporuke;

    private String vrstaPostupka;

    private Double bodCijena;

    private Double bodRok;

    private Double bodUkupno;

    private String karakteristikaSpecifikacije;

    private String karakteristikaPonude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSifraPostupka() {
        return sifraPostupka;
    }

    public void setSifraPostupka(Integer sifraPostupka) {
        this.sifraPostupka = sifraPostupka;
    }

    public String getNazivPonudjaca() {
        return nazivPonudjaca;
    }

    public void setNazivPonudjaca(String nazivPonudjaca) {
        this.nazivPonudjaca = nazivPonudjaca;
    }

    public Integer getSifraPonude() {
        return sifraPonude;
    }

    public void setSifraPonude(Integer sifraPonude) {
        this.sifraPonude = sifraPonude;
    }

    public String getAtc() {
        return atc;
    }

    public void setAtc(String atc) {
        this.atc = atc;
    }

    public Integer getTrazenaKolicina() {
        return trazenaKolicina;
    }

    public void setTrazenaKolicina(Integer trazenaKolicina) {
        this.trazenaKolicina = trazenaKolicina;
    }

    public Double getProcijenjenaVrijednost() {
        return procijenjenaVrijednost;
    }

    public void setProcijenjenaVrijednost(Double procijenjenaVrijednost) {
        this.procijenjenaVrijednost = procijenjenaVrijednost;
    }

    public String getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public void setNazivProizvodjaca(String nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
    }

    public String getZasticeniNaziv() {
        return zasticeniNaziv;
    }

    public void setZasticeniNaziv(String zasticeniNaziv) {
        this.zasticeniNaziv = zasticeniNaziv;
    }

    public Double getJedinicnaCijena() {
        return jedinicnaCijena;
    }

    public void setJedinicnaCijena(Double jedinicnaCijena) {
        this.jedinicnaCijena = jedinicnaCijena;
    }

    public Double getPonudjenaVrijednost() {
        return ponudjenaVrijednost;
    }

    public void setPonudjenaVrijednost(Double ponudjenaVrijednost) {
        this.ponudjenaVrijednost = ponudjenaVrijednost;
    }

    public Integer getRokIsporuke() {
        return rokIsporuke;
    }

    public void setRokIsporuke(Integer rokIsporuke) {
        this.rokIsporuke = rokIsporuke;
    }

    public String getVrstaPostupka() {
        return vrstaPostupka;
    }

    public void setVrstaPostupka(String vrstaPostupka) {
        this.vrstaPostupka = vrstaPostupka;
    }

    public Double getBodCijena() {
        return bodCijena;
    }

    public void setBodCijena(Double bodCijena) {
        this.bodCijena = bodCijena;
    }

    public Double getBodRok() {
        return bodRok;
    }

    public void setBodRok(Double bodRok) {
        this.bodRok = bodRok;
    }

    public Double getBodUkupno() {
        return bodUkupno;
    }

    public void setBodUkupno(Double bodUkupno) {
        this.bodUkupno = bodUkupno;
    }

    public String getKarakteristikaSpecifikacije() {
        return karakteristikaSpecifikacije;
    }

    public void setKarakteristikaSpecifikacije(String karakteristikaSpecifikacije) {
        this.karakteristikaSpecifikacije = karakteristikaSpecifikacije;
    }

    public String getKarakteristikaPonude() {
        return karakteristikaPonude;
    }

    public void setKarakteristikaPonude(String karakteristikaPonude) {
        this.karakteristikaPonude = karakteristikaPonude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewPrvorangiraniDTO)) {
            return false;
        }

        ViewPrvorangiraniDTO viewPrvorangiraniDTO = (ViewPrvorangiraniDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, viewPrvorangiraniDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewPrvorangiraniDTO{" +
            "id=" + getId() +
            ", sifraPostupka=" + getSifraPostupka() +
            ", nazivPonudjaca='" + getNazivPonudjaca() + "'" +
            ", sifraPonude=" + getSifraPonude() +
            ", atc='" + getAtc() + "'" +
            ", trazenaKolicina=" + getTrazenaKolicina() +
            ", procijenjenaVrijednost=" + getProcijenjenaVrijednost() +
            ", nazivProizvodjaca='" + getNazivProizvodjaca() + "'" +
            ", zasticeniNaziv='" + getZasticeniNaziv() + "'" +
            ", jedinicnaCijena=" + getJedinicnaCijena() +
            ", ponudjenaVrijednost=" + getPonudjenaVrijednost() +
            ", rokIsporuke=" + getRokIsporuke() +
            ", vrstaPostupka='" + getVrstaPostupka() + "'" +
            ", bodCijena=" + getBodCijena() +
            ", bodRok=" + getBodRok() +
            ", bodUkupno=" + getBodUkupno() +
            ", karakteristikaSpecifikacije='" + getKarakteristikaSpecifikacije() + "'" +
            ", karakteristikaPonude='" + getKarakteristikaPonude() + "'" +
            "}";
    }
}
