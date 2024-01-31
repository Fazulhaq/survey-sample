package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InternetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Internet.class);
        Internet internet1 = new Internet();
        internet1.setId(1L);
        Internet internet2 = new Internet();
        internet2.setId(internet1.getId());
        assertThat(internet1).isEqualTo(internet2);
        internet2.setId(2L);
        assertThat(internet1).isNotEqualTo(internet2);
        internet1.setId(null);
        assertThat(internet1).isNotEqualTo(internet2);
    }
}
