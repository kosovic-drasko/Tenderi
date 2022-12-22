package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class ViewVrednovanjeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewVrednovanje.class);
        ViewVrednovanje viewVrednovanje1 = new ViewVrednovanje();
        viewVrednovanje1.setId(1L);
        ViewVrednovanje viewVrednovanje2 = new ViewVrednovanje();
        viewVrednovanje2.setId(viewVrednovanje1.getId());
        assertThat(viewVrednovanje1).isEqualTo(viewVrednovanje2);
        viewVrednovanje2.setId(2L);
        assertThat(viewVrednovanje1).isNotEqualTo(viewVrednovanje2);
        viewVrednovanje1.setId(null);
        assertThat(viewVrednovanje1).isNotEqualTo(viewVrednovanje2);
    }
}
