package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class HvalePonudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HvalePonude.class);
        HvalePonude hvalePonude1 = new HvalePonude();
        hvalePonude1.setId(1L);
        HvalePonude hvalePonude2 = new HvalePonude();
        hvalePonude2.setId(hvalePonude1.getId());
        assertThat(hvalePonude1).isEqualTo(hvalePonude2);
        hvalePonude2.setId(2L);
        assertThat(hvalePonude1).isNotEqualTo(hvalePonude2);
        hvalePonude1.setId(null);
        assertThat(hvalePonude1).isNotEqualTo(hvalePonude2);
    }
}
