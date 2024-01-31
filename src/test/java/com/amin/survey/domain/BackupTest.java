package com.amin.survey.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amin.survey.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BackupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Backup.class);
        Backup backup1 = new Backup();
        backup1.setId(1L);
        Backup backup2 = new Backup();
        backup2.setId(backup1.getId());
        assertThat(backup1).isEqualTo(backup2);
        backup2.setId(2L);
        assertThat(backup1).isNotEqualTo(backup2);
        backup1.setId(null);
        assertThat(backup1).isNotEqualTo(backup2);
    }
}
