package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NetworkConfigCheckListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetworkConfigCheckList.class);
        NetworkConfigCheckList networkConfigCheckList1 = new NetworkConfigCheckList();
        networkConfigCheckList1.setId(1L);
        NetworkConfigCheckList networkConfigCheckList2 = new NetworkConfigCheckList();
        networkConfigCheckList2.setId(networkConfigCheckList1.getId());
        assertThat(networkConfigCheckList1).isEqualTo(networkConfigCheckList2);
        networkConfigCheckList2.setId(2L);
        assertThat(networkConfigCheckList1).isNotEqualTo(networkConfigCheckList2);
        networkConfigCheckList1.setId(null);
        assertThat(networkConfigCheckList1).isNotEqualTo(networkConfigCheckList2);
    }
}
