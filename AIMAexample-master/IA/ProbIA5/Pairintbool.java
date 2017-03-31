package IA.ProbIA5;

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

    public String toString(){
        String a = "ID: "+ ID + ", is sensor: " + isSensor;
        return a;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pairintbool))
            return false;
        if (obj == this)
            return true;
        Pairintbool p = (Pairintbool) obj;
        return ID == p.getID() && p.isSensor == isSensor;
    }
}
