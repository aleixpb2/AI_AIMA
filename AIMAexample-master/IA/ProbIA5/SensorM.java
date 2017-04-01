package IA.ProbIA5;

import IA.Red.Sensor;

public class SensorM {

    private Sensor sensor;
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

/*    public SensorM(int capacidad, int cx, int cy, Pairintbool p) {
        sensor = new Sensor(capacidad, cx, cy);
        currentCap = capacidad;
        last = p;
    }*/

    public SensorM(Sensor s, int id) { // used when created: last are themselves
        sensor = new Sensor((int)s.getCapacidad(), s.getCoordX(), s.getCoordY());
        this.currentCap = s.getCapacidad();
        last = new Pairintbool(id, true);
    }

    public SensorM(Sensor s, double curCap, Pairintbool lst) { // used to clone
        sensor = s; // not a copy!
        currentCap = curCap;
        last = new Pairintbool(lst.getID(), lst.isSensor()); // deep copy
    }

    public SensorM myClone(){
        return new SensorM(sensor, currentCap, last);
    }

    // This implementation now requires additional methods:
    public int getCoordX() {
        return sensor.getCoordX();
    }

    public void setCoordX(int CoordX) {
        sensor.setCoordX(CoordX);
    }

    public int getCoordY() {
        return sensor.getCoordY();
    }

    public void setCoordY(int CoordY) {
        sensor.setCoordY(CoordY);
    }

    public double getCapacidad() {
        return (double)sensor.getCapacidad();
    }

    public void setCapacidad(int capacidad) {
        sensor.setCapacidad(capacidad);
    }
}
