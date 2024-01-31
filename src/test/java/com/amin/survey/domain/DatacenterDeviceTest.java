package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatacenterDeviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatacenterDevice.class);
        DatacenterDevice datacenterDevice1 = new DatacenterDevice();
        datacenterDevice1.setId(1L);
        DatacenterDevice datacenterDevice2 = new DatacenterDevice();
        datacenterDevice2.setId(datacenterDevice1.getId());
        assertThat(datacenterDevice1).isEqualTo(datacenterDevice2);
        datacenterDevice2.setId(2L);
        assertThat(datacenterDevice1).isNotEqualTo(datacenterDevice2);
        datacenterDevice1.setId(null);
        assertThat(datacenterDevice1).isNotEqualTo(datacenterDevice2);
    }
}
