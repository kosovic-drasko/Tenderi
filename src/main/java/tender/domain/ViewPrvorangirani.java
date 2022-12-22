package tender.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ViewPrvorangirani.
 */
@Entity
@Table(name = "view_prvorangirani")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewPrvorangirani implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sifra_postupka")
    private Integer sifraPostupka;

    @Column(name = "naziv_ponudjaca")
    private String nazivPonudjaca;

    @Column(name = "sifra_ponude")
    private Integer sifraPonude;

    @Column(name = "atc")
    private String atc;

    @Column(name = "trazena_kolicina")
    private Integer trazenaKolicina;

    @Column(name = "procijenjena_vrijednost")
    private Double procijenjenaVrijednost;

    @Column(name = "naziv_proizvodjaca")
    private String nazivProizvodjaca;

    @Column(name = "zasticeni_naziv")
    private String zasticeniNaziv;

    @Column(name = "jedinicna_cijena")
    private Double jedinicnaCijena;

    @Column(name = "ponudjena_vrijednost")
    private Double ponudjenaVrijednost;

    @Column(name = "rok_isporuke")
    private Integer rokIsporuke;

    @Column(name = "vrsta_postupka")
    private String vrstaPostupka;

    @Column(name = "bod_cijena")
    private Double bodCijena;

    @Column(name = "bod_rok")
    private Double bodRok;

    @Column(name = "bod_ukupno")
    private Double bodUkupno;

    @Column(name = "karakteristika_specifikacije")
    private String karakteristikaSpecifikacije;

    @Column(name = "karakteristika_ponude")
    private String karakteristikaPonude;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ViewPrvorangirani id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSifraPostupka() {
        return this.sifraPostupka;
    }

    public ViewPrvorangirani sifraPostupka(Integer sifraPostupka) {
        this.setSifraPostupka(sifraPostupka);
        return this;
    }

    public void setSifraPostupka(Integer sifraPostupka) {
        this.sifraPostupka = sifraPostupka;
    }

    public String getNazivPonudjaca() {
        return this.nazivPonudjaca;
    }

    public ViewPrvorangirani nazivPonudjaca(String nazivPonudjaca) {
        this.setNazivPonudjaca(nazivPonudjaca);
        return this;
    }

    public void setNazivPonudjaca(String nazivPonudjaca) {
        this.nazivPonudjaca = nazivPonudjaca;
    }

    public Integer getSifraPonude() {
        return this.sifraPonude;
    }

    public ViewPrvorangirani sifraPonude(Integer sifraPonude) {
        this.setSifraPonude(sifraPonude);
        return this;
    }

    public void setSifraPonude(Integer sifraPonude) {
        this.sifraPonude = sifraPonude;
    }

    public String getAtc() {
        return this.atc;
    }

    public ViewPrvorangirani atc(String atc) {
        this.setAtc(atc);
        return this;
    }

    public void setAtc(String atc) {
        this.atc = atc;
    }

    public Integer getTrazenaKolicina() {
        return this.trazenaKolicina;
    }

    public ViewPrvorangirani trazenaKolicina(Integer trazenaKolicina) {
        this.setTrazenaKolicina(trazenaKolicina);
        return this;
    }

    public void setTrazenaKolicina(Integer trazenaKolicina) {
        this.trazenaKolicina = trazenaKolicina;
    }

    public Double getProcijenjenaVrijednost() {
        return this.procijenjenaVrijednost;
    }

    public ViewPrvorangirani procijenjenaVrijednost(Double procijenjenaVrijednost) {
        this.setProcijenjenaVrijednost(procijenjenaVrijednost);
        return this;
    }

    public void setProcijenjenaVrijednost(Double procijenjenaVrijednost) {
        this.procijenjenaVrijednost = procijenjenaVrijednost;
    }

    public String getNazivProizvodjaca() {
        return this.nazivProizvodjaca;
    }

    public ViewPrvorangirani nazivProizvodjaca(String nazivProizvodjaca) {
        this.setNazivProizvodjaca(nazivProizvodjaca);
        return this;
    }

    public void setNazivProizvodjaca(String nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
    }

    public String getZasticeniNaziv() {
        return this.zasticeniNaziv;
    }

    public ViewPrvorangirani zasticeniNaziv(String zasticeniNaziv) {
        this.setZasticeniNaziv(zasticeniNaziv);
        return this;
    }

    public void setZasticeniNaziv(String zasticeniNaziv) {
        this.zasticeniNaziv = zasticeniNaziv;
    }

    public Double getJedinicnaCijena() {
        return this.jedinicnaCijena;
    }

    public ViewPrvorangirani jedinicnaCijena(Double jedinicnaCijena) {
        this.setJedinicnaCijena(jedinicnaCijena);
        return this;
    }

    public void setJedinicnaCijena(Double jedinicnaCijena) {
        this.jedinicnaCijena = jedinicnaCijena;
    }

    public Double getPonudjenaVrijednost() {
        return this.ponudjenaVrijednost;
    }

    public ViewPrvorangirani ponudjenaVrijednost(Double ponudjenaVrijednost) {
        this.setPonudjenaVrijednost(ponudjenaVrijednost);
        return this;
    }

    public void setPonudjenaVrijednost(Double ponudjenaVrijednost) {
        this.ponudjenaVrijednost = ponudjenaVrijednost;
    }

    public Integer getRokIsporuke() {
        return this.rokIsporuke;
    }

    public ViewPrvorangirani rokIsporuke(Integer rokIsporuke) {
        this.setRokIsporuke(rokIsporuke);
        return this;
    }

    public void setRokIsporuke(Integer rokIsporuke) {
        this.rokIsporuke = rokIsporuke;
    }

    public String getVrstaPostupka() {
        return this.vrstaPostupka;
    }

    public ViewPrvorangirani vrstaPostupka(String vrstaPostupka) {
        this.setVrstaPostupka(vrstaPostupka);
        return this;
    }

    public void setVrstaPostupka(String vrstaPostupka) {
        this.vrstaPostupka = vrstaPostupka;
    }

    public Double getBodCijena() {
        return this.bodCijena;
    }

    public ViewPrvorangirani bodCijena(Double bodCijena) {
        this.setBodCijena(bodCijena);
        return this;
    }

    public void setBodCijena(Double bodCijena) {
        this.bodCijena = bodCijena;
    }

    public Double getBodRok() {
        return this.bodRok;
    }

    public ViewPrvorangirani bodRok(Double bodRok) {
        this.setBodRok(bodRok);
        return this;
    }

    public void setBodRok(Double bodRok) {
        this.bodRok = bodRok;
    }

    public Double getBodUkupno() {
        return this.bodUkupno;
    }

    public ViewPrvorangirani bodUkupno(Double bodUkupno) {
        this.setBodUkupno(bodUkupno);
        return this;
    }

    public void setBodUkupno(Double bodUkupno) {
        this.bodUkupno = bodUkupno;
    }

    public String getKarakteristikaSpecifikacije() {
        return this.karakteristikaSpecifikacije;
    }

    public ViewPrvorangirani karakteristikaSpecifikacije(String karakteristikaSpecifikacije) {
        this.setKarakteristikaSpecifikacije(karakteristikaSpecifikacije);
        return this;
    }

    public void setKarakteristikaSpecifikacije(String karakteristikaSpecifikacije) {
        this.karakteristikaSpecifikacije = karakteristikaSpecifikacije;
    }

    public String getKarakteristikaPonude() {
        return this.karakteristikaPonude;
    }

    public ViewPrvorangirani karakteristikaPonude(String karakteristikaPonude) {
        this.setKarakteristikaPonude(karakteristikaPonude);
        return this;
    }

    public void setKarakteristikaPonude(String karakteristikaPonude) {
        this.karakteristikaPonude = karakteristikaPonude;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewPrvorangirani)) {
            return false;
        }
        return id != null && id.equals(((ViewPrvorangirani) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewPrvorangirani{" +
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
