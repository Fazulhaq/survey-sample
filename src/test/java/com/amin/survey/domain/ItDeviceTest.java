package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItDeviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItDevice.class);
        ItDevice itDevice1 = new ItDevice();
        itDevice1.setId(1L);
        ItDevice itDevice2 = new ItDevice();
        itDevice2.setId(itDevice1.getId());
        assertThat(itDevice1).isEqualTo(itDevice2);
        itDevice2.setId(2L);
        assertThat(itDevice1).isNotEqualTo(itDevice2);
        itDevice1.setId(null);
        assertThat(itDevice1).isNotEqualTo(itDevice2);
    }
}
