package hu.u_szeged.inf.fog.simulator.application.domain.model;

import java.io.File;
import java.util.stream.Stream;

import org.springframework.data.annotation.Id;
import org.springframework.util.MultiValueMap;

import hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType;
import hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ResultFileType;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.SimulatorJobResult;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimulatorJob {

    @Id
    private String id;
    private String user;
    private Long priority;
    private Long numberOfCalculation;
    private SimulatorJobStatus simulatorJobStatus;
    private MultiValueMap<ConfigFileType, File> configFiles;
    private MultiValueMap<ResultFileType, File> resultFiles;
    private SimulatorJobResult simulatorJobResult;

    public boolean isValid() {
        return Stream.of(ConfigFileType.values())
            .map(configFiles::containsKey)
            .allMatch(Boolean::booleanValue);
    }
}
