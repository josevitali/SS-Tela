package ar.edu.itba;

public class Spring {
    private double k;
    private double gamma;
    private double restDistance;

    public Spring(double k, double restDistance) {
        this.k = k;
        this.gamma = 0;
        this.restDistance = restDistance;
    }

    public Spring(double k, double gamma, double restDistance) {
        this.k = k;
        this.gamma = gamma;
        this.restDistance = restDistance;
    }
}
