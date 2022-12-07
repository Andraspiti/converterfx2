package hu.u_szeged.inf.fog.simulator.application.dao.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJobStatus;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.SimulatorJobResult;
import lombok.Builder;
import lombok.Data;

/**
 * Data object (DO) for the simulator-jobs collection, which is storing the dissect-cf jobs.
 *
 * @author Balazs Lehoczki
 */
@Data
@Builder
@Document("simulator_jobs")
public class SimulatorJobDO {

    @Id
    private String id;
    private String user;
    private Long priority;
    private Long numberOfCalculation;
    private SimulatorJobStatus simulatorJobStatus;
    private Map<String, ObjectId> configFiles;
    private Map<String, ObjectId> resultFiles;
    private SimulatorJobResult simulatorJobResult;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
