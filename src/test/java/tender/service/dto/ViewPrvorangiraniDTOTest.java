package tender.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tender.web.rest.TestUtil;

class ViewPrvorangiraniDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPrvorangiraniDTO.class);
        ViewPrvorangiraniDTO viewPrvorangiraniDTO1 = new ViewPrvorangiraniDTO();
        viewPrvorangiraniDTO1.setId(1L);
        ViewPrvorangiraniDTO viewPrvorangiraniDTO2 = new ViewPrvorangiraniDTO();
        assertThat(viewPrvorangiraniDTO1).isNotEqualTo(viewPrvorangiraniDTO2);
        viewPrvorangiraniDTO2.setId(viewPrvorangiraniDTO1.getId());
        assertThat(viewPrvorangiraniDTO1).isEqualTo(viewPrvorangiraniDTO2);
        viewPrvorangiraniDTO2.setId(2L);
        assertThat(viewPrvorangiraniDTO1).isNotEqualTo(viewPrvorangiraniDTO2);
        viewPrvorangiraniDTO1.setId(null);
        assertThat(viewPrvorangiraniDTO1).isNotEqualTo(viewPrvorangiraniDTO2);
    }
}
