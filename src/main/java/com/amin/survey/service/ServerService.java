package com.amin.survey.service;

import com.amin.survey.domain.Server;
import com.amin.survey.repository.ServerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.amin.survey.domain.Server}.
 */
@Service
@Transactional
public class ServerService {

    private final Logger log = LoggerFactory.getLogger(ServerService.class);

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * Save a server.
     *
     * @param server the entity to save.
     * @return the persisted entity.
     */
    public Server save(Server server) {
        log.debug("Request to save Server : {}", server);
        return serverRepository.save(server);
    }

    /**
     * Update a server.
     *
     * @param server the entity to save.
     * @return the persisted entity.
     */
    public Server update(Server server) {
        log.debug("Request to update Server : {}", server);
        return serverRepository.save(server);
    }

    /**
     * Partially update a server.
     *
     * @param server the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Server> partialUpdate(Server server) {
        log.debug("Request to partially update Server : {}", server);

        return serverRepository
            .findById(server.getId())
            .map(existingServer -> {
                if (server.getQuestion1() != null) {
                    existingServer.setQuestion1(server.getQuestion1());
                }
                if (server.getQuestion2() != null) {
                    existingServer.setQuestion2(server.getQuestion2());
                }
                if (server.getQuestion3() != null) {
                    existingServer.setQuestion3(server.getQuestion3());
                }
                if (server.getQuestion4() != null) {
                    existingServer.setQuestion4(server.getQuestion4());
                }
                if (server.getQuestion5() != null) {
                    existingServer.setQuestion5(server.getQuestion5());
                }
                if (server.getQuestion6() != null) {
                    existingServer.setQuestion6(server.getQuestion6());
                }
                if (server.getQuestion7() != null) {
                    existingServer.setQuestion7(server.getQuestion7());
                }

                return existingServer;
            })
            .map(serverRepository::save);
    }

    /**
     * Get all the servers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Server> findAll(Pageable pageable) {
        log.debug("Request to get all Servers");
        return serverRepository.findAll(pageable);
    }

    /**
     * Get one server by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Server> findOne(Long id) {
        log.debug("Request to get Server : {}", id);
        return serverRepository.findById(id);
    }

    /**
     * Delete the server by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Server : {}", id);
        serverRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Server> findByFormId(Long id) {
        log.debug("Request to get Server : {}", id);
        return serverRepository.findByFormId(id);
    }
}
