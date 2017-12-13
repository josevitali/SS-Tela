package ar.edu.itba;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Spring {
    private double k;
    private double gamma;
    private double restDistance;


    public Vector3D getForce(Vector3D position1, Vector3D position2) {
        Vector3D distance = position2.subtract(position1);
        double norm = distance.getNorm();
        return distance.scalarMultiply(-this.k * (norm-restDistance)/norm);
    }

    public Vector3D getForce(Vector3D position1, Vector3D position2, Vector3D previous1, Vector3D previous2, double dt) {
        Vector3D distance = position2.subtract(position1);
        Vector3D previousDistance = previous2.subtract(previous1);
        double norm = distance.getNorm();
        double x = norm - restDistance;
        double disp = norm - previousDistance.getNorm();
        return distance
                .scalarMultiply(-k*x -this.gamma*(disp/dt))
                .scalarMultiply(1.0/norm);
    }

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
