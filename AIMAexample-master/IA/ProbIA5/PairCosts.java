package IA.ProbIA5;

/**
 * Created by laiafreixas on 29/03/17.
 */
public class PairCosts {
    private double first;
    private double second;

    public PairCosts(double first, double second) {
        this.first = first;
        this.second = second;
    }

    public double getFirst() {
        return first;
    }

    public void setFirst(double first) {
        this.first = first;
    }

    public double getSecond() {
        return second;
    }

    public void setSecond(double second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "PairCosts{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
