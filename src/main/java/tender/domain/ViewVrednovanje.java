package tender.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ViewVrednovanje.
 */
@Entity
@Table(name = "view_vrednovanje")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewVrednovanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sifra_postupka")
    private Integer sifraPostupka;

    @Column(name = "sifra_ponude")
    private Integer sifraPonude;

    @Column(name = "broj_partije")
    private Integer brojPartije;

    @Column(name = "naziv_proizvodjaca")
    private String nazivProizvodjaca;

    @Column(name = "zasticeni_naziv")
    private String zasticeniNaziv;

    @Column(name = "ponudjena_vrijednost")
    private Integer ponudjenaVrijednost;

    @Column(name = "rok_isporuke")
    private Integer rokIsporuke;

    @Column(name = "jedinicna_cijena")
    private Double jedinicnaCijena;

    @Column(name = "naziv_ponudjaca")
    private String nazivPonudjaca;

    @Column(name = "atc")
    private String atc;

    @Column(name = "trazena_kolicina")
    private Integer trazenaKolicina;

    @Column(name = "procijenjena_vrijednost")
    private Double procijenjenaVrijednost;

    @Column(name = "vrsta_postupka")
    private String vrstaPostupka;

    @Column(name = "katekteristika_ponude")
    private String katekteristikaPonude;

    @Column(name = "karakteristika_specifikacije")
    private String karakteristikaSpecifikacije;

    @Column(name = "bod_cijena")
    private Double bodCijena;

    @Column(name = "bod_rok")
    private Double bodRok;

    @Column(name = "bod_ukupno")
    private Double bodUkupno;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ViewVrednovanje id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSifraPostupka() {
        return this.sifraPostupka;
    }

    public ViewVrednovanje sifraPostupka(Integer sifraPostupka) {
        this.setSifraPostupka(sifraPostupka);
        return this;
    }

    public void setSifraPostupka(Integer sifraPostupka) {
        this.sifraPostupka = sifraPostupka;
    }

    public Integer getSifraPonude() {
        return this.sifraPonude;
    }

    public ViewVrednovanje sifraPonude(Integer sifraPonude) {
        this.setSifraPonude(sifraPonude);
        return this;
    }

    public void setSifraPonude(Integer sifraPonude) {
        this.sifraPonude = sifraPonude;
    }

    public Integer getBrojPartije() {
        return this.brojPartije;
    }

    public ViewVrednovanje brojPartije(Integer brojPartije) {
        this.setBrojPartije(brojPartije);
        return this;
    }

    public void setBrojPartije(Integer brojPartije) {
        this.brojPartije = brojPartije;
    }

    public String getNazivProizvodjaca() {
        return this.nazivProizvodjaca;
    }

    public ViewVrednovanje nazivProizvodjaca(String nazivProizvodjaca) {
        this.setNazivProizvodjaca(nazivProizvodjaca);
        return this;
    }

    public void setNazivProizvodjaca(String nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
    }

    public String getZasticeniNaziv() {
        return this.zasticeniNaziv;
    }

    public ViewVrednovanje zasticeniNaziv(String zasticeniNaziv) {
        this.setZasticeniNaziv(zasticeniNaziv);
        return this;
    }

    public void setZasticeniNaziv(String zasticeniNaziv) {
        this.zasticeniNaziv = zasticeniNaziv;
    }

    public Integer getPonudjenaVrijednost() {
        return this.ponudjenaVrijednost;
    }

    public ViewVrednovanje ponudjenaVrijednost(Integer ponudjenaVrijednost) {
        this.setPonudjenaVrijednost(ponudjenaVrijednost);
        return this;
    }

    public void setPonudjenaVrijednost(Integer ponudjenaVrijednost) {
        this.ponudjenaVrijednost = ponudjenaVrijednost;
    }

    public Integer getRokIsporuke() {
        return this.rokIsporuke;
    }

    public ViewVrednovanje rokIsporuke(Integer rokIsporuke) {
        this.setRokIsporuke(rokIsporuke);
        return this;
    }

    public void setRokIsporuke(Integer rokIsporuke) {
        this.rokIsporuke = rokIsporuke;
    }

    public Double getJedinicnaCijena() {
        return this.jedinicnaCijena;
    }

    public ViewVrednovanje jedinicnaCijena(Double jedinicnaCijena) {
        this.setJedinicnaCijena(jedinicnaCijena);
        return this;
    }

    public void setJedinicnaCijena(Double jedinicnaCijena) {
        this.jedinicnaCijena = jedinicnaCijena;
    }

    public String getNazivPonudjaca() {
        return this.nazivPonudjaca;
    }

    public ViewVrednovanje nazivPonudjaca(String nazivPonudjaca) {
        this.setNazivPonudjaca(nazivPonudjaca);
        return this;
    }

    public void setNazivPonudjaca(String nazivPonudjaca) {
        this.nazivPonudjaca = nazivPonudjaca;
    }

    public String getAtc() {
        return this.atc;
    }

    public ViewVrednovanje atc(String atc) {
        this.setAtc(atc);
        return this;
    }

    public void setAtc(String atc) {
        this.atc = atc;
    }

    public Integer getTrazenaKolicina() {
        return this.trazenaKolicina;
    }

    public ViewVrednovanje trazenaKolicina(Integer trazenaKolicina) {
        this.setTrazenaKolicina(trazenaKolicina);
        return this;
    }

    public void setTrazenaKolicina(Integer trazenaKolicina) {
        this.trazenaKolicina = trazenaKolicina;
    }

    public Double getProcijenjenaVrijednost() {
        return this.procijenjenaVrijednost;
    }

    public ViewVrednovanje procijenjenaVrijednost(Double procijenjenaVrijednost) {
        this.setProcijenjenaVrijednost(procijenjenaVrijednost);
        return this;
    }

    public void setProcijenjenaVrijednost(Double procijenjenaVrijednost) {
        this.procijenjenaVrijednost = procijenjenaVrijednost;
    }

    public String getVrstaPostupka() {
        return this.vrstaPostupka;
    }

    public ViewVrednovanje vrstaPostupka(String vrstaPostupka) {
        this.setVrstaPostupka(vrstaPostupka);
        return this;
    }

    public void setVrstaPostupka(String vrstaPostupka) {
        this.vrstaPostupka = vrstaPostupka;
    }

    public String getKatekteristikaPonude() {
        return this.katekteristikaPonude;
    }

    public ViewVrednovanje katekteristikaPonude(String katekteristikaPonude) {
        this.setKatekteristikaPonude(katekteristikaPonude);
        return this;
    }

    public void setKatekteristikaPonude(String katekteristikaPonude) {
        this.katekteristikaPonude = katekteristikaPonude;
    }

    public String getKarakteristikaSpecifikacije() {
        return this.karakteristikaSpecifikacije;
    }

    public ViewVrednovanje karakteristikaSpecifikacije(String karakteristikaSpecifikacije) {
        this.setKarakteristikaSpecifikacije(karakteristikaSpecifikacije);
        return this;
    }

    public void setKarakteristikaSpecifikacije(String karakteristikaSpecifikacije) {
        this.karakteristikaSpecifikacije = karakteristikaSpecifikacije;
    }

    public Double getBodCijena() {
        return this.bodCijena;
    }

    public ViewVrednovanje bodCijena(Double bodCijena) {
        this.setBodCijena(bodCijena);
        return this;
    }

    public void setBodCijena(Double bodCijena) {
        this.bodCijena = bodCijena;
    }

    public Double getBodRok() {
        return this.bodRok;
    }

    public ViewVrednovanje bodRok(Double bodRok) {
        this.setBodRok(bodRok);
        return this;
    }

    public void setBodRok(Double bodRok) {
        this.bodRok = bodRok;
    }

    public Double getBodUkupno() {
        return this.bodUkupno;
    }

    public ViewVrednovanje bodUkupno(Double bodUkupno) {
        this.setBodUkupno(bodUkupno);
        return this;
    }

    public void setBodUkupno(Double bodUkupno) {
        this.bodUkupno = bodUkupno;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewVrednovanje)) {
            return false;
        }
        return id != null && id.equals(((ViewVrednovanje) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewVrednovanje{" +
            "id=" + getId() +
            ", sifraPostupka=" + getSifraPostupka() +
            ", sifraPonude=" + getSifraPonude() +
            ", brojPartije=" + getBrojPartije() +
            ", nazivProizvodjaca='" + getNazivProizvodjaca() + "'" +
            ", zasticeniNaziv='" + getZasticeniNaziv() + "'" +
            ", ponudjenaVrijednost=" + getPonudjenaVrijednost() +
            ", rokIsporuke=" + getRokIsporuke() +
            ", jedinicnaCijena=" + getJedinicnaCijena() +
            ", nazivPonudjaca='" + getNazivPonudjaca() + "'" +
            ", atc='" + getAtc() + "'" +
            ", trazenaKolicina=" + getTrazenaKolicina() +
            ", procijenjenaVrijednost=" + getProcijenjenaVrijednost() +
            ", vrstaPostupka='" + getVrstaPostupka() + "'" +
            ", katekteristikaPonude='" + getKatekteristikaPonude() + "'" +
            ", karakteristikaSpecifikacije='" + getKarakteristikaSpecifikacije() + "'" +
            ", bodCijena=" + getBodCijena() +
            ", bodRok=" + getBodRok() +
            ", bodUkupno=" + getBodUkupno() +
            "}";
    }
}
