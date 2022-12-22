package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class ViewPonudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPonude.class);
        ViewPonude viewPonude1 = new ViewPonude();
        viewPonude1.setId(1L);
        ViewPonude viewPonude2 = new ViewPonude();
        viewPonude2.setId(viewPonude1.getId());
        assertThat(viewPonude1).isEqualTo(viewPonude2);
        viewPonude2.setId(2L);
        assertThat(viewPonude1).isNotEqualTo(viewPonude2);
        viewPonude1.setId(null);
        assertThat(viewPonude1).isNotEqualTo(viewPonude2);
    }
}
