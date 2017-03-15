package IA.ProbIA5;

import IA.Red.Sensor;

/**
 * Created by aleix.paris on 15/03/2017.
 */
public class SensorM extends Sensor {

    private double currentCap;

    public double getCurrentCap() {
        return currentCap;
    }

    public void setCurrentCap(double currentCap) {
        this.currentCap = currentCap;
    }

    public SensorM(int capacidad, int cx, int cy) {
        super(capacidad, cx, cy);
        currentCap = capacidad;
    }

    public SensorM(Sensor s) {
        super((int)s.getCapacidad(), s.getCoordX(), s.getCoordY());
        this.currentCap = s.getCapacidad();
    }
}
