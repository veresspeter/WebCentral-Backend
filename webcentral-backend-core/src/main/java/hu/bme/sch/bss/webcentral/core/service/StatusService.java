package hu.bme.sch.bss.webcentral.core.service;

import hu.bme.sch.bss.webcentral.core.dao.StatusDao;
import hu.bme.sch.bss.webcentral.core.domain.StatusRequest;
import hu.bme.sch.bss.webcentral.core.model.Status;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("designforextension")
public class StatusService {
    private static final String STATUS_CREATE_STARTED = "Status creation started. {}";
    private static final String STATUS_CREATE_SUCCEED = "Status creation succeed. {}";
    private static final String STATUS_SEARCH_STARTED = "Status search started {}";
    private static final String STATUS_SEARCH_SUCCEED = "Status search succeed {}";
    private static final String STATUS_NOT_FOUND = "Status not found with id: {}";
    private static final String STATUS_DELETE_STARTED = "Status delete started. {}";
    private static final String STATUS_DELETE_SUCCEED = "Status delete succeed. {}";
    private static final String STATUS_EDIT_STARTED = "Status edit started. {}";
    private static final String STATUS_EDIT_SUCCEED = "Status edit succeed. {}";

    private final StatusDao statusDao;
    private final Logger logger;

    public StatusService(final StatusDao statusDao, final Logger logger) {
        this.statusDao = statusDao;
        this.logger = logger;
    }

    public Status create(final StatusRequest request) {
        logger.info(STATUS_CREATE_STARTED, request);
        Status status = createStatusWithRequestData(request);
        statusDao.save(status);
        logger.info(STATUS_CREATE_SUCCEED, request);
        return status;
    }

    public Status findById(final Long id) {
        logger.info(STATUS_SEARCH_STARTED, id);
        Optional<Status> result = statusDao.findById(id);
        if (result.isEmpty()) {
            logger.warn(STATUS_NOT_FOUND, id);
            throw new NoSuchElementException("Status not found");
        }
        logger.info(STATUS_SEARCH_SUCCEED, id);
        return result.get();
    }

    public void delete(final Status status) {
        logger.info(STATUS_DELETE_STARTED, status);
        statusDao.delete(status);
        logger.info(STATUS_DELETE_SUCCEED, status);
    }

    Status createStatusWithRequestData(final StatusRequest request) {
        return Status.builder()
            .withName(request.getName())
            .build();
    }

    public void update(final StatusRequest request, final Status status) {
        logger.info(STATUS_EDIT_STARTED, request);
        status.setName(request.getName());
        logger.info(STATUS_EDIT_SUCCEED, request);
    }
}