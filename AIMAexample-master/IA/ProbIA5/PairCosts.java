package IA.ProbIA5;

public class PairCosts {
    private double first; // v*distance()^2
    private double second; // sum( distance() )

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
