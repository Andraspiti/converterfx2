package hu.u_szeged.inf.fog.simulator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Executor application for reading and processing the next available simulation job.
 *
 * @author Balazs Lehoczki
 */
@EnableScheduling
@EnableMongoAuditing
@SpringBootApplication
public class DissectCFApplication {

    public static void main(String[] args) {
        SpringApplication.run(DissectCFApplication.class, args);
    }
}
