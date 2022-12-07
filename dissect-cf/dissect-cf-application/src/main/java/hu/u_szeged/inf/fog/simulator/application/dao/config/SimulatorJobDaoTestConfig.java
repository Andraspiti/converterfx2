package hu.u_szeged.inf.fog.simulator.application.dao.config;

import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.APPLIANCES_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.DEVICES_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.IAAS_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.INSTANCES_FILE;
import static hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType.PROVIDERS_FILE;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import hu.u_szeged.inf.fog.simulator.application.dao.SimulatorJobSaverGridFsDao;
import hu.u_szeged.inf.fog.simulator.application.dao.model.SimulatorJobDO;
import hu.u_szeged.inf.fog.simulator.application.domain.model.SimulatorJobStatus;
import hu.u_szeged.inf.fog.simulator.application.domain.model.filetype.ConfigFileType;

/**
 * Configuration class for populating the Mongodb database with initial data to help local development.
 * It's being executed only in local profile mode.
 *
 * @author Balazs Lehoczki
 */
@Configuration
@Profile("local")
public class SimulatorJobDaoTestConfig {

    private static final Long TEST_PRIORITY = 1000L;

    @Bean
    CommandLineRunner commandLineRunner(MongoTemplate mongoTemplate, SimulatorJobSaverGridFsDao saverGridFsDao) {
        return strings -> {
            var envConfig = createTestEnvFilesMap();
            var envConfigDO = saverGridFsDao.saveFiles(envConfig);
            var simulatorJobDO = createTestJob(envConfigDO);

            //mongoTemplate.remove(new Query(), SimulatorJobDO.class);
            mongoTemplate.save(simulatorJobDO);
        };
    }

    private MultiValueMap<ConfigFileType, File> createTestEnvFilesMap() throws URISyntaxException {
        MultiValueMap<ConfigFileType, File> map = new LinkedMultiValueMap();

        map.add(APPLIANCES_FILE, this.getTestFileFor("demo/XML_examples/applications.xml"));
        map.add(DEVICES_FILE, this.getTestFileFor("demo/XML_examples/devices.xml"));
        map.add(INSTANCES_FILE, this.getTestFileFor("demo/XML_examples/instances.xml"));
        map.add(PROVIDERS_FILE, this.getTestFileFor("demo/XML_examples/providers.xml"));
        map.add(IAAS_FILE, this.getTestFileFor("demo/LPDS_original.xml"));
        map.add(IAAS_FILE, this.getTestFileFor("demo/XML_examples/LPDS_32.xml"));
        map.add(IAAS_FILE, this.getTestFileFor("demo/XML_examples/LPDS_16.xml"));

        return map;
    }

    private SimulatorJobDO createTestJob(Map<String, ObjectId> inputConfigDO) {
        return SimulatorJobDO.builder()
            .id(UUID.randomUUID().toString())
            .user("test_user")
            .priority(TEST_PRIORITY)
            .configFiles(inputConfigDO)
            .simulatorJobStatus(SimulatorJobStatus.SUBMITTED)
            .numberOfCalculation(0L)
            .build();
    }

    private File getTestFileFor(String resourcePath) throws URISyntaxException {
        return new File(ClassLoader.getSystemResource(resourcePath).toURI());
    }
}
