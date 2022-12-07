package hu.u_szeged.inf.fog.simulator.application.domain.model.result;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Cost {

    private double totalCostInEuro;
    private double bluemixCostInEuro;
    private double amazonCostInEuro;
    private double azureCostInEuro;
    private double oracleCostInEuro;
}
