package main.rocket;

public class Chamber extends Rocket {
    protected Double OxygenConcentration;

    public void setOxygenConcentration(Double oxygenConcentration) {
        this.OxygenConcentration = oxygenConcentration;
    }

    public Double getOxygenConcentration() {
        return OxygenConcentration;
    }
    public void monitorOxygenLevel() {
        if (getOxygenConcentration() < 15.0) {
            System.out.println("Warning: Oxygen level critical! Initiating oxygen refill... ");
            OxygenConcentration = 100.0;  // Восстановление кислорода
            System.out.println("Oxygen refilled! ");
        } else {
            System.out.println("Oxygen level is stable: " + OxygenConcentration + "% ");
        }
    }
}
