package hu.u_szeged.inf.fog.simulator.application.demo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import hu.u_szeged.inf.fog.simulator.application.Application;
import hu.u_szeged.inf.fog.simulator.application.Application.VmCollector;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.ActuatorEvents;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.Architecture;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.DataVolume;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.Cost;
import hu.u_szeged.inf.fog.simulator.application.domain.model.result.SimulatorJobResult;
import hu.u_szeged.inf.fog.simulator.iot.Device;
import hu.u_szeged.inf.fog.simulator.iot.actuator.Actuator;
import hu.u_szeged.inf.fog.simulator.iot.actuator.ChangeNodeActuatorEvent;
import hu.u_szeged.inf.fog.simulator.iot.actuator.ChangePositionActuatorEvent;
import hu.u_szeged.inf.fog.simulator.iot.actuator.ConnectToNodeActuatorEvent;
import hu.u_szeged.inf.fog.simulator.iot.actuator.DisconnectFromNodeActuatorEvent;
import hu.u_szeged.inf.fog.simulator.physical.ComputingAppliance;

/**
 * Helper class to manage and visualize the simulation (logging).
 */
public abstract class ScenarioBase {

    /**
     * Path to the resource files.
     */
    public final static String resourcePath = new StringBuilder(System.getProperty("user.dir"))
        .append(File.separator)
        .append("src")
        .append(File.separator)
        .append("main")
        .append(File.separator)
        .append("resources")
        .append(File.separator)
        .append("demo")
        .append(File.separator)
        .toString();

    /**
     * It prints the most important characteristics of the actual simulation and
     * class the time line and chart generators.
     *
     * @param runtime Execution time
     * @return With the printed result as Java POJO
     */
    public static SimulatorJobResult printInformation(long runtime) {
        System.out.println("~~Information about the simulation:~~");

        double totalCost = 0;
        long generatedData = 0, processedData = 0, arrivedData = 0;
        int usedVM = 0;
        int tasks = 0;
        double totalEnergyConsumptionOfNodes = 0.0;
        double totalEnergyConsumptionOfDevices = 0.0;
        long highestDeviceStopTime = Long.MIN_VALUE;
        long highestApplicationStopTime = Long.MIN_VALUE;
        long makespan = Long.MIN_VALUE;
        double bluemix = 0;
        double amazon = 0;
        double azure = 0;
        double oracle = 0;

        for (ComputingAppliance c : ComputingAppliance.allComputingAppliance) {
            System.out.println();
            System.out.println("Computing Appliance: " + c.name + " energy consumption: " + c.energyConsumption);

            totalEnergyConsumptionOfNodes += c.energyConsumption;

            for (Application a : c.applicationList) {
                System.out.println("Application: " + a.name);

                totalCost += a.instance.calculateCloudCost(a.sumOfWorkTime);
                processedData += a.sumOfProcessedData;
                arrivedData += a.sumOfArrivedData;
                usedVM += a.vmCollectorList.size();

                for (VmCollector vmcl : a.vmCollectorList) {
                    tasks += vmcl.taskCounter;
                    System.out.println(
                        "VM-" + vmcl.id + " tasks: " + vmcl.taskCounter + " worktime: " + vmcl.workTime + " installed" +
                        " at: " +
                        vmcl.installed + " restarted: " + vmcl.restarted);
                }

                for (Device d : a.deviceList) {
                    generatedData += d.sumOfGeneratedData;
                    totalEnergyConsumptionOfDevices += d.energyConsumption;

                    if (d.stopTime > highestDeviceStopTime) {
                        highestDeviceStopTime = d.stopTime;
                    }
                }

                if (a.stopTime > highestApplicationStopTime) {
                    highestApplicationStopTime = a.stopTime;
                }

                System.out.println(a.name + " devices: " + a.deviceList.size() + " node cost:" + a.instance
                    .calculateCloudCost(a.sumOfWorkTime));

                if (!a.providers.isEmpty()) {

                    System.out.println(a.providers);
                    bluemix += a.providers.get(0).cost;
                    amazon += a.providers.get(1).cost;
                    azure += a.providers.get(2).cost;
                    oracle += a.providers.get(3).cost;
                }

            }

            System.out.println();
        }

        makespan = highestApplicationStopTime - highestDeviceStopTime;

        System.out.println("VMs (pc.): " + usedVM + " tasks (pc.): " + tasks);
        System.out.println("Node cost (Euro): " + totalCost);
        System.out.println(
            "IoT cost (Euro) -  Bluemix: " + bluemix + " Amazon: " + amazon + " Azure: " + azure + " Oracle: " + oracle);
        System.out.println(
            "Nodes energy (W): " + totalEnergyConsumptionOfNodes + " Devices energy (W): " + totalEnergyConsumptionOfDevices);
        System.out.println(
            "Network (seconds): " + TimeUnit.SECONDS.convert(Application.sumOfTimeOnNetwork, TimeUnit.MILLISECONDS));
        System.out.println("Network (MB): " + (Application.sumOfByteOnNetwork / 1024 / 1024));
        System.out.println("Timeout (minutes): " + ((double) makespan / 1000 / 60));
        System.out.println(
            "App STOP (hours): " + ((double) highestApplicationStopTime / 1000 / 60 / 60) + " Device STOP (hours):" + ((double) highestDeviceStopTime / 1000 / 60 / 60));
        System.out.println(
            "Generated / processed / arrived data (bytes): " + generatedData + "/" + processedData + "/" + arrivedData + " (~" + (arrivedData / 1024 / 1024) + " MB)");
        System.out.println("Runtime (seconds): " + TimeUnit.SECONDS.convert(runtime, TimeUnit.NANOSECONDS));
        System.out.println("Number of actuator events: " + Actuator.actuatorEventCounter);
        System.out.println("Number of actuator evens:\n" +
                           "\tChangeNode: " + ChangeNodeActuatorEvent.changeNodeEventCounter +
                           "\n\tChangePosition: " + ChangePositionActuatorEvent.changePositionEventCounter +
                           "\n\tConnectToNode: " + ConnectToNodeActuatorEvent.connectToNodeEventCounter +
                           "\n\tDisconnectFromNode: " + DisconnectFromNodeActuatorEvent.DisconnectFromNodeEventCounter);

        final var actuatorEvents = ActuatorEvents.builder()
            .eventCount(Actuator.actuatorEventCounter)
            .nodeChange(ChangeNodeActuatorEvent.changeNodeEventCounter)
            .positionChange(ChangePositionActuatorEvent.changePositionEventCounter)
            .connectToNodeEventCount(ConnectToNodeActuatorEvent.connectToNodeEventCounter)
            .disconnectFromNodeEventCount(DisconnectFromNodeActuatorEvent.DisconnectFromNodeEventCounter)
            .build();

        final var architecture = Architecture.builder()
            .tasks(tasks)
            .usedVirtualMachines(usedVM)
            .totalEnergyConsumptionOfNodesInWatt(totalEnergyConsumptionOfNodes)
            .totalEnergyConsumptionOfDevicesInWatt(totalEnergyConsumptionOfDevices)
            .sumOfMillisecondsOnNetwork(Application.sumOfTimeOnNetwork)
            .sumOfByteOnNetwork(Application.sumOfByteOnNetwork)
            .highestAppStopTimeInHour((double) highestApplicationStopTime / 1000 / 60 / 60)
            .highestDeviceStopTimeInHour((double) highestDeviceStopTime / 1000 / 60 / 60)
            .build();

        final var cost = Cost.builder()
            .totalCostInEuro(totalCost)
            .bluemixCostInEuro(bluemix)
            .amazonCostInEuro(amazon)
            .azureCostInEuro(azure)
            .oracleCostInEuro(oracle)
            .build();

        final var dataVolume = DataVolume.builder()
            .generatedDataInBytes(generatedData)
            .processedDataInBytes(processedData)
            .arrivedDataInBytes(arrivedData)
            .build();

        return SimulatorJobResult.builder()
            .architecture(architecture)
            .actuatorEvents(actuatorEvents)
            .cost(cost)
            .dataVolume(dataVolume)
            .timeoutInMinutes((double) makespan / 1000 / 60)
            .build();
    }
}