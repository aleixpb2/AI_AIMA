package IA.ProbIA5;

import IA.Red.Sensor;

public class SensorM extends Sensor {

    private double currentCap;

    private Pairintbool last; // last node in the connection tree that this sensor belongs to

    public double getCurrentCap() {
        return currentCap;
    }

    public void setCurrentCap(double currentCap) {
        this.currentCap = currentCap;
    }

    public Pairintbool getLast() {
        return last;
    }

    public void setLast(Pairintbool last) {
        this.last = last;
    }

    public SensorM(int capacidad, int cx, int cy, int id) {
        super(capacidad, cx, cy);

        currentCap = capacidad;
        last = new Pairintbool(id, true);
    }

    public SensorM(Sensor s, int id) {
        super((int)s.getCapacidad(), s.getCoordX(), s.getCoordY());
        this.currentCap = s.getCapacidad();
        last = new Pairintbool(id, true);
    }

    public SensorM copy (){
        return new SensorM((int)this.getCapacidad(),this.getCoordX(),this.getCoordY(),this.getLast().getID());
    }
}
