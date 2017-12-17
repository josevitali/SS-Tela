package ar.edu.itba.springs;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.particles.Particle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class TorsionSpring implements Spring{

    private final double k;
    private final double restAngle;
    private final double dt;
    private final FabricParticle particle1;
    private final FabricParticle particle2;
    private final FabricParticle middle;
    private final double gamma;
    private double previousAngle;

    /*public TorsionSpring(double k, double naturalAngle) {
        this.k = k;
        this.restAngle = naturalAngle;
    }*/

    public TorsionSpring(FabricParticle particle1, FabricParticle particle2, FabricParticle middle, double k, double restAngle, double dt) {

        this.particle1 = particle1;
        this.particle2 = particle2;
        this.middle = middle;
        this.k = k;
        this.restAngle = restAngle;
        this.dt = dt;

        // 2* sqrt(2/5*K*M*R^2)
        this.gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * middle.getMass() * middle.getRadius() * middle.getRadius());

        final Vector3D distanceVector1 = middle.getPosition().subtract(particle1.getPosition());
        final Vector3D distanceVector2 = particle2.getPosition().subtract(middle.getPosition());

        this.previousAngle = Vector3D.angle(distanceVector1, distanceVector2);
    }


    public void apply() {

        final Vector3D distanceVector1 = middle.getPosition().subtract(particle1.getPosition());
        final Vector3D distanceVector2 = particle2.getPosition().subtract(middle.getPosition());

        final double angle = Vector3D.angle(distanceVector1, distanceVector2);

        if (angle == restAngle) {
            return;
        }

        double springTorque = -k * (angle - restAngle);

        //DAMPING
            springTorque -= gamma * (angle - previousAngle) / dt;

        final double distance1 = distanceVector1.getNorm();
        final double distance2 = distanceVector2.getNorm();

        final double springForce1 = springTorque / distance1;
        final double springForce2 = springTorque / distance2;

        final Vector3D orthogonalVector = distanceVector1.crossProduct(distanceVector2);

        final Vector3D springForceVersor1 = orthogonalVector.crossProduct(distanceVector1);
        final Vector3D springForceVector1 = springForceVersor1.scalarMultiply(springForce1 / springForceVersor1.getNorm());
        if(!particle1.isUnmovable()) {
            particle1.addForce(springForceVector1);
        }
        final Vector3D springForceVersor2 = orthogonalVector.crossProduct(distanceVector2);
        final Vector3D springForceVector2 = springForceVersor2.scalarMultiply(springForce2 / springForceVersor2.getNorm());
        if(!particle2.isUnmovable()) {
            particle2.addForce(springForceVector2);
        }

        previousAngle = angle;

    }




    public Vector3D getForce(Particle particle1, Particle particle2, Particle middle, double dt) {

        final double gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * middle.getMass() * middle.getRadius() * middle.getRadius());

        Vector3D position1 = particle1.getPosition();
        Vector3D position2 = particle2.getPosition();
        Vector3D previous1 = particle2.getPreviousPosition();
        Vector3D previous2 = particle2.getPreviousPosition();
        Vector3D middlePosition = middle.getPosition();
        Vector3D middlePrevious = middle.getPreviousPosition();


        final Vector3D distance1 = middlePosition.subtract(position1);
        final Vector3D distance2 = position2.subtract(middlePosition);

        final Vector3D prevDistance1 = middlePrevious.subtract(previous1);
        final Vector3D prevDistance2 = previous2.subtract(middlePrevious);

        final double angle = Vector3D.angle(distance1, distance2);

        if(angle == restAngle) {
            return Vector3D.ZERO;
        }

        final double previousAngle = Vector3D.angle(prevDistance1, prevDistance2);

        double torque = -k * (angle - restAngle);// - gamma * (angle - previousAngle) / dt;

        final double force = torque / distance1.getNorm();

        final Vector3D orthogonalVector = distance1.crossProduct(distance2);

        final Vector3D forceVersor = orthogonalVector.crossProduct(distance1);
        final Vector3D torsionForceVector = forceVersor.scalarMultiply(force / forceVersor.getNorm());


        return torsionForceVector;
    }

}
