package IA.ProbIA5;

/**
 * Created by laiafreixas on 8/03/17.
 */
public class Pairintbool {
    private int ID;
    private boolean isSensor;

    public Pairintbool(int ID, boolean isSensor) {
        this.isSensor = isSensor;
        this.ID = ID;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void setSensor(boolean sensor) {
        isSensor = sensor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
