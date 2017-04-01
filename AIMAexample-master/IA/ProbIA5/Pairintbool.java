package IA.ProbIA5;

import java.util.Objects;

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
        //return this.toString().hashCode();
        return Objects.hash(ID, isSensor); // hash code is computed by computing the hash codes for the relevant fields
        // and combining them. Both is left to Objects' utility function hash.
    }

    @Override
    public final boolean equals(Object obj){
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Pairintbool))
            return false;
        Pairintbool p = (Pairintbool) obj;
        //return ID == p.getID() && p.isSensor == isSensor;
        return Objects.equals(ID, p.getID()) // Objects.equals checks if fields are null. Java 1.7 or more required.
                && Objects.equals(isSensor, p.isSensor);
    }
}
