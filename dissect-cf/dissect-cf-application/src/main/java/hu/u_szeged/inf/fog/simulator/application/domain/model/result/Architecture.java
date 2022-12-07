package hu.u_szeged.inf.fog.simulator.application.domain.model.result;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Architecture {

    private int usedVirtualMachines;
    private int tasks;
    private double totalEnergyConsumptionOfNodesInWatt;
    private double totalEnergyConsumptionOfDevicesInWatt;
    private double sumOfMillisecondsOnNetwork;
    private double sumOfByteOnNetwork;
    private double highestAppStopTimeInHour;
    private double highestDeviceStopTimeInHour;
}
