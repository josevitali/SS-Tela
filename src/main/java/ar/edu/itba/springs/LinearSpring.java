package ar.edu.itba.springs;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.particles.Particle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LinearSpring {
    private double k;
    private double restDistance;


    public Vector3D getForce(Vector3D position1, Vector3D position2) {
        Vector3D distance = position2.subtract(position1);
        double norm = distance.getNorm();
        return distance.scalarMultiply(-this.k * (norm-restDistance)/norm);
    }

    public Vector3D getForce(Particle particle1, Particle particle2, double dt) {

        final double gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * particle1.getMass() * particle1.getRadius() * particle1.getRadius());

        Vector3D position1 = particle1.getPosition();
        Vector3D position2 = particle2.getPosition();
        Vector3D previous1 = particle1.getPreviousPosition();
        Vector3D previous2 = particle2.getPreviousPosition();

        Vector3D distance = position2.subtract(position1);
        Vector3D previousDistance = previous2.subtract(previous1);
        double norm = distance.getNorm();
        double x = norm - restDistance;
        double disp = norm - previousDistance.getNorm();
        return distance
                .scalarMultiply(-k*x -gamma*(disp/dt))
                .scalarMultiply(1.0/norm);
    }

    public LinearSpring(double k, double restDistance) {
        this.k = k;
        this.restDistance = restDistance;
    }
}
