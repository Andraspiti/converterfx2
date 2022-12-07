# DISSECT-CF-Application
This module contains the application related code of the DISSECT-CF simulator.

## Getting started
After a successful maven compile:

1. Run the Job Executor:
   ```commandline
   java -jar ./dissect-cf-application/target/dissect-cf-fog-application-1.0.0-SNAPSHOT.jar
   ```

2. Run a demo example:
   ```commandline
   java -cp ./dissect-cf-application/target/dissect-cf-fog-application-1.0.0-SNAPSHOT.jar\
        -Dloader.main=hu.u_szeged.inf.fog.simulator.application.demo.XMLSimulation\
        org.springframework.boot.loader.PropertiesLauncher
   ```
