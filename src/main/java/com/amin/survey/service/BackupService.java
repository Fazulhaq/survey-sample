package com.amin.survey.service;

import com.amin.survey.domain.Backup;
import com.amin.survey.repository.BackupRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.Backup}.
 */
@Service
@Transactional
public class BackupService {

    private final Logger log = LoggerFactory.getLogger(BackupService.class);

    private final BackupRepository backupRepository;

    public BackupService(BackupRepository backupRepository) {
        this.backupRepository = backupRepository;
    }

    /**
     * Save a backup.
     *
     * @param backup the entity to save.
     * @return the persisted entity.
     */
    public Backup save(Backup backup) {
        log.debug("Request to save Backup : {}", backup);
        return backupRepository.save(backup);
    }

    /**
     * Update a backup.
     *
     * @param backup the entity to save.
     * @return the persisted entity.
     */
    public Backup update(Backup backup) {
        log.debug("Request to update Backup : {}", backup);
        return backupRepository.save(backup);
    }

    /**
     * Partially update a backup.
     *
     * @param backup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Backup> partialUpdate(Backup backup) {
        log.debug("Request to partially update Backup : {}", backup);

        return backupRepository
            .findById(backup.getId())
            .map(existingBackup -> {
                if (backup.getQuestion1() != null) {
                    existingBackup.setQuestion1(backup.getQuestion1());
                }
                if (backup.getQuestion2() != null) {
                    existingBackup.setQuestion2(backup.getQuestion2());
                }
                if (backup.getQuestion3() != null) {
                    existingBackup.setQuestion3(backup.getQuestion3());
                }
                if (backup.getQuestion4() != null) {
                    existingBackup.setQuestion4(backup.getQuestion4());
                }
                if (backup.getQuestion5() != null) {
                    existingBackup.setQuestion5(backup.getQuestion5());
                }
                if (backup.getQuestion6() != null) {
                    existingBackup.setQuestion6(backup.getQuestion6());
                }
                if (backup.getQuestion7() != null) {
                    existingBackup.setQuestion7(backup.getQuestion7());
                }

                return existingBackup;
            })
            .map(backupRepository::save);
    }

    /**
     * Get all the backups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Backup> findAll(Pageable pageable) {
        log.debug("Request to get all Backups");
        return backupRepository.findAll(pageable);
    }

    /**
     * Get one backup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Backup> findOne(Long id) {
        log.debug("Request to get Backup : {}", id);
        return backupRepository.findById(id);
    }

    /**
     * Delete the backup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Backup : {}", id);
        backupRepository.deleteById(id);
    }
}
