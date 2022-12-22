package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class VrednovanjeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vrednovanje.class);
        Vrednovanje vrednovanje1 = new Vrednovanje();
        vrednovanje1.setId(1L);
        Vrednovanje vrednovanje2 = new Vrednovanje();
        vrednovanje2.setId(vrednovanje1.getId());
        assertThat(vrednovanje1).isEqualTo(vrednovanje2);
        vrednovanje2.setId(2L);
        assertThat(vrednovanje1).isNotEqualTo(vrednovanje2);
        vrednovanje1.setId(null);
        assertThat(vrednovanje1).isNotEqualTo(vrednovanje2);
    }
}
