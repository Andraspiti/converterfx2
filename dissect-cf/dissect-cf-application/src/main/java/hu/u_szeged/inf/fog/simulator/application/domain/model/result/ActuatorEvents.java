package hu.u_szeged.inf.fog.simulator.application.domain.model.result;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ActuatorEvents {

    private long eventCount;
    private long nodeChange;
    private long positionChange;
    private long connectToNodeEventCount;
    private long disconnectFromNodeEventCount;
}
