package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgResponsiblePersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgResponsiblePerson.class);
        OrgResponsiblePerson orgResponsiblePerson1 = new OrgResponsiblePerson();
        orgResponsiblePerson1.setId(1L);
        OrgResponsiblePerson orgResponsiblePerson2 = new OrgResponsiblePerson();
        orgResponsiblePerson2.setId(orgResponsiblePerson1.getId());
        assertThat(orgResponsiblePerson1).isEqualTo(orgResponsiblePerson2);
        orgResponsiblePerson2.setId(2L);
        assertThat(orgResponsiblePerson1).isNotEqualTo(orgResponsiblePerson2);
        orgResponsiblePerson1.setId(null);
        assertThat(orgResponsiblePerson1).isNotEqualTo(orgResponsiblePerson2);
    }
}
