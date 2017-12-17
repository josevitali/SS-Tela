package ar.edu.itba.springs;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.particles.Particle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LinearSpring implements Spring{
    private final FabricParticle particle1;
    private final FabricParticle particle2;
    private final double k;
    private final double restDistance;
    private final double dt;
    private final double gamma;
    private double previousDistance;


    public Vector3D getForce(Vector3D position1, Vector3D position2) {
        Vector3D distance = position2.subtract(position1);
        double norm = distance.getNorm();
        return distance.scalarMultiply(-this.k * (norm-restDistance)/norm);
    }

    public Vector3D getForce(FabricParticle particle1, FabricParticle particle2, double dt) {

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
        Vector3D force = distance
                            .scalarMultiply(-k*x -gamma*(disp/dt))
                            .scalarMultiply(1.0/norm).negate();
        if(force.getNorm() != 0) {
            return force;
        }
        return Vector3D.ZERO;
    }

    /*public LinearSpring(double k, double restDistance) {
        this.k = k;
        this.restDistance = restDistance;
    }*/

    public LinearSpring(FabricParticle particle1, FabricParticle particle2, double k, double restDistance, double dt) {

        this.particle1 = particle1;
        this.particle2 = particle2;
        this.k = k;
        this.restDistance = restDistance;
        this.dt = dt;


        // 2* sqrt(2/5*K*M*R^2)
        this.gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * particle1.getMass() * particle1.getRadius() * particle1.getRadius());

        final Vector3D distanceVector = particle2.getPosition().subtract(particle1.getPosition());
        this.previousDistance = distanceVector.getNorm();
    }

    public void apply() {

        final Vector3D distanceVector = particle2.getPosition().subtract(particle1.getPosition());

        double particleDistance = distanceVector.getNorm();
        double springForce = -k * (particleDistance - restDistance);

        //DAMPING
            springForce -= gamma * (particleDistance - previousDistance) / dt;

        final Vector3D springForceVector = distanceVector.scalarMultiply(springForce / distanceVector.getNorm());

        if (springForceVector.getNorm() != 0) {
            if(!particle2.isUnmovable()) {
                particle2.addForce(springForceVector);
            }
            if(!particle1.isUnmovable()) {
                particle1.addForce(springForceVector.negate());
            }
        }

        previousDistance = particleDistance;

    }
}
