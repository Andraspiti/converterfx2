package hu.u_szeged.inf.fog.simulator.application.dao;

import static hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJobStatus.FAILED;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJobStatus.PROCESSED;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJobStatus.PROCESSING;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJobStatus.SUBMITTED;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import hu.u_szeged.inf.fog.simulator.application.dao.model.SimulatorJobDO;
import hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJob;
import hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ResultFileType;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.SimulatorJobResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SimulatorJobDao {

    private static final Sort JOB_SORTING = Sort.by(Sort.Order.desc("priority"));
    private static final String NUMBER_OF_CALCULATION_FIELD_NAME = "numberOfCalculation";
    private static final String JOB_STATUS_FIELD_NAME = "simulatorJobStatus";
    private static final String ID_FIELD_NAME = "_id";

    @NonNull
    private final MongoTemplate mongoTemplate;
    @NonNull
    private final SimulatorJobRetrieverGridFsDao retrieverGridFsDao;
    @NonNull
    private final SimulatorJobSaverGridFsDao saverGridFsDao;
    @Value("${dissect-cf.jobs.max_number_of_calculation}")
    private int maxNumberOfCalculation;

    public SimulatorJob retrieveNextAvailableJob() {
        var calculationCriteria = Criteria.where(NUMBER_OF_CALCULATION_FIELD_NAME).lte(maxNumberOfCalculation);
        var failedJobCriteria = Criteria.where(JOB_STATUS_FIELD_NAME).is(FAILED).andOperator(calculationCriteria);
        var submittedCriteria = Criteria.where(JOB_STATUS_FIELD_NAME).is(SUBMITTED);
        var jobCriteria = new Criteria().orOperator(submittedCriteria, failedJobCriteria);

        var query = Query.query(jobCriteria).with(JOB_SORTING);
        var updateStatus = Update.update(JOB_STATUS_FIELD_NAME, PROCESSING);
        var simulatorJobDO = mongoTemplate.findAndModify(query, updateStatus, SimulatorJobDO.class);

        return convertJobDataObjectToDomain(simulatorJobDO);
    }

    public void saveSimulatorJobResult(@NonNull String id,
                                       @NonNull MultiValueMap<ResultFileType, File> resultFiles,
                                       @NonNull SimulatorJobResult simulatorJobResult) {
        var savedDBFiles = saverGridFsDao.saveFiles(resultFiles);
        var query = Query.query(Criteria.where(ID_FIELD_NAME).is(id));
        var update = Update.update("results", savedDBFiles)
            .set("simulatorJobResult", simulatorJobResult)
            .set(JOB_STATUS_FIELD_NAME, PROCESSED);

        mongoTemplate.updateFirst(query, update, SimulatorJobDO.class);
    }

    public void saveSimulatorJobError(@NonNull String id, long numberOfCalculation) {
        var query = Query.query(Criteria.where(ID_FIELD_NAME).is(id));
        var update = Update.update(JOB_STATUS_FIELD_NAME, FAILED)
            .set(NUMBER_OF_CALCULATION_FIELD_NAME, numberOfCalculation);

        mongoTemplate.updateFirst(query, update, SimulatorJobDO.class);
    }

    private SimulatorJob convertJobDataObjectToDomain(SimulatorJobDO simulatorJobDO) {
        return Optional.ofNullable(simulatorJobDO)
            .map(job -> SimulatorJob.builder()
                .id(job.getId())
                .user(job.getUser())
                .priority(job.getPriority())
                .numberOfCalculation(job.getNumberOfCalculation())
                .simulatorJobStatus(job.getSimulatorJobStatus())
                .configFiles(retrieverGridFsDao.retrieveFiles(job.getConfigFiles(), job.getId()))
                .build())
            .orElse(null);
    }
}
