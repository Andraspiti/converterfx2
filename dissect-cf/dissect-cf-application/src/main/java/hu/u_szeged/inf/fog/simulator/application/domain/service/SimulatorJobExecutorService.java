package hu.u_szeged.inf.fog.simulator.application.domain.service;

import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.APPLIANCES_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.DEVICES_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.IAAS_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.INSTANCES_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.PROVIDERS_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.util.SimulatorJobFileUtil.WORKING_DIR;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.u_szeged.inf.fog.simulator.application.dao.SimulatorJobDao;
import hu.u_szeged.inf.fog.simulator.application.demo.ScenarioBase;
import hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJob;
import hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ResultFileType;
import hu.u_szeged.inf.fog.simulator.iot.Station;
import hu.u_szeged.inf.fog.simulator.physical.ComputingAppliance;
import hu.u_szeged.inf.fog.simulator.providers.Instance;
import hu.u_szeged.inf.fog.simulator.providers.Provider;
import hu.u_szeged.inf.fog.simulator.util.TimelineGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SimulatorJobExecutorService {

    @NonNull
    private final SimulatorJobDao simulatorJobDao;

    @Scheduled(fixedDelayString = "${dissect-cf.jobs.delay_in_milliseconds}")
    public void runSimulationForTheNextAvailableJob() {
        final var startTime = System.nanoTime();

        Optional.ofNullable(simulatorJobDao.retrieveNextAvailableJob())
            .filter(SimulatorJob::isValid)
            .stream()
            .peek(this::loadSimulationData)
            .peek(job -> Timed.simulateUntilLastEvent())
            .peek(job -> this.saveSimulatorResults(job, startTime))
            .findFirst();
    }

    private void loadSimulationData(@NonNull SimulatorJob simulatorJob) {
        var simulatorJobConfigs = simulatorJob.getConfigFiles();
        var iaasLoaders = Optional.ofNullable(simulatorJobConfigs.get(IAAS_FILE))
            .stream()
            .flatMap(List::stream)
            .map(file -> Map.entry(file.getName().replaceFirst(".xml", ""), file.getAbsolutePath()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Station.loadDevicesXML(simulatorJobConfigs.getFirst(DEVICES_FILE).getPath());
        Instance.loadInstancesXML(simulatorJobConfigs.getFirst(INSTANCES_FILE).getPath());
        ComputingAppliance.loadAppliancesXML(simulatorJobConfigs.getFirst(APPLIANCES_FILE).getPath(), iaasLoaders);
        Provider.loadProvidersXML(simulatorJobConfigs.getFirst(PROVIDERS_FILE).getPath());
    }

    private void saveSimulatorResults(@NonNull SimulatorJob simulatorJob, long startTime) {
        try {
            var result = ScenarioBase.printInformation(System.nanoTime() - startTime);
            var resultFiles = new LinkedMultiValueMap<ResultFileType, File>();
            var timelinePathToSave = WORKING_DIR + "/" + simulatorJob.getId();
            var timeline = TimelineGenerator.generate(timelinePathToSave);

            resultFiles.add(ResultFileType.TIMELINE, timeline);
            simulatorJobDao.saveSimulatorJobResult(simulatorJob.getId(), resultFiles, result);
        } catch (IOException e) {
            var numberOfCalculation = simulatorJob.getNumberOfCalculation() + 1;

            simulatorJobDao.saveSimulatorJobError(simulatorJob.getId(), numberOfCalculation);
            throw new IllegalStateException("Couldn't save simulator result onto the filesystem!", e);
        }
    }
}
