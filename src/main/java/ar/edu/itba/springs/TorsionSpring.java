package ar.edu.itba.springs;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.particles.Particle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class TorsionSpring {

    private final double k;
    private final double restAngle;

    public TorsionSpring(double k, double naturalAngle) {
        this.k = k;
        this.restAngle = naturalAngle;
    }

    public Vector3D getForce(Particle particle1, Particle particle2, Particle middle, double dt) {


        //public Vector3D getForce(Vector3D position1, Vector3D position2, Vector3D middle, Vector3D previous1, Vector3D previous2, double dt) {

        final double gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * middle.getMass() * middle.getRadius() * middle.getRadius());

        Vector3D position1 = particle1.getPosition();
        Vector3D position2 = particle2.getPosition();
        Vector3D previous1 = particle2.getPreviousPosition();
        Vector3D previous2 = particle2.getPreviousPosition();
        Vector3D middleDistance = middle.getPosition();



        final Vector3D distance1 = middleDistance.subtract(position1);
        final Vector3D distance2 = position2.subtract(middleDistance);

        final double angle = Vector3D.angle(distance1, distance2);

        if(angle == restAngle) {
            return new Vector3D(0,0,0);
        }

        final double previousAngle = Vector3D.angle(distance1, distance2);

        double torque = -k * (angle - restAngle) - gamma * (angle - previousAngle) / dt;

        final double force = torque / distance1.getNorm();

        final Vector3D orthogonalVector = distance1.crossProduct(distance2);

        final Vector3D forceVersor = orthogonalVector.crossProduct(distance1);
        final Vector3D torsionForceVector = forceVersor.scalarMultiply(force / forceVersor.getNorm());


        return torsionForceVector;
    }

}
