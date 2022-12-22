package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class TenderiHomeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenderiHome.class);
        TenderiHome tenderiHome1 = new TenderiHome();
        tenderiHome1.setId(1L);
        TenderiHome tenderiHome2 = new TenderiHome();
        tenderiHome2.setId(tenderiHome1.getId());
        assertThat(tenderiHome1).isEqualTo(tenderiHome2);
        tenderiHome2.setId(2L);
        assertThat(tenderiHome1).isNotEqualTo(tenderiHome2);
        tenderiHome1.setId(null);
        assertThat(tenderiHome1).isNotEqualTo(tenderiHome2);
    }
}
