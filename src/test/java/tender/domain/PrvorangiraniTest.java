package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class PrvorangiraniTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prvorangirani.class);
        Prvorangirani prvorangirani1 = new Prvorangirani();
        prvorangirani1.setId(1L);
        Prvorangirani prvorangirani2 = new Prvorangirani();
        prvorangirani2.setId(prvorangirani1.getId());
        assertThat(prvorangirani1).isEqualTo(prvorangirani2);
        prvorangirani2.setId(2L);
        assertThat(prvorangirani1).isNotEqualTo(prvorangirani2);
        prvorangirani1.setId(null);
        assertThat(prvorangirani1).isNotEqualTo(prvorangirani2);
    }
}
