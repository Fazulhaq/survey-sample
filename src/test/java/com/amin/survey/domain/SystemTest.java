package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SystemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(System.class);
        System system1 = new System();
        system1.setId(1L);
        System system2 = new System();
        system2.setId(system1.getId());
        assertThat(system1).isEqualTo(system2);
        system2.setId(2L);
        assertThat(system1).isNotEqualTo(system2);
        system1.setId(null);
        assertThat(system1).isNotEqualTo(system2);
    }
}
