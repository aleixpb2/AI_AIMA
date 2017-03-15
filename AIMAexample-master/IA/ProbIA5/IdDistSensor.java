package IA.ProbIA5;

/**
 * Created by laiafreixas on 8/03/17.
 */
public class IdDistSensor {
    private int ID;
    private double dist;

    public IdDistSensor(int ID, double dist, boolean isSensor) {
        this.ID = ID;
        this.dist = dist;
        this.isSensor = isSensor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void setSensor(boolean sensor) {
        isSensor = sensor;
    }

    private boolean isSensor;
}
