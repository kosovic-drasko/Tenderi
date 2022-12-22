package tender.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class ViewPrvorangiraniTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPrvorangirani.class);
        ViewPrvorangirani viewPrvorangirani1 = new ViewPrvorangirani();
        viewPrvorangirani1.setId(1L);
        ViewPrvorangirani viewPrvorangirani2 = new ViewPrvorangirani();
        viewPrvorangirani2.setId(viewPrvorangirani1.getId());
        assertThat(viewPrvorangirani1).isEqualTo(viewPrvorangirani2);
        viewPrvorangirani2.setId(2L);
        assertThat(viewPrvorangirani1).isNotEqualTo(viewPrvorangirani2);
        viewPrvorangirani1.setId(null);
        assertThat(viewPrvorangirani1).isNotEqualTo(viewPrvorangirani2);
    }
}
