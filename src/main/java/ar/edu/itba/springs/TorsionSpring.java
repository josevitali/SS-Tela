package ar.edu.itba.springs;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class TorsionSpring {

    private final double k;
    private final double gamma;
    private final double restAngle;

    public TorsionSpring(double k, double gamma, double naturalAngle) {
        this.k = k;
        this.gamma = gamma;
        this.restAngle = naturalAngle;
    }

    public Vector3D getForce(Vector3D position1, Vector3D position2, Vector3D middle, Vector3D previous1, Vector3D previous2, Vector3D previousMiddle, double dt) {

        final Vector3D distance1 = middle.subtract(position1);
        final Vector3D distance2 = position2.subtract(middle);

        final double angle = Vector3D.angle(distance1, distance2);
        final double previousAngle = Vector3D.angle(previous1, previous2);

        double torque = -k * (angle - restAngle) - gamma * (angle - previousAngle) / dt;

        final double force = torque / distance1.getNorm();

        final Vector3D orthogonalVector = distance1.crossProduct(distance2);

        final Vector3D forceVersor = orthogonalVector.crossProduct(distance1);
        final Vector3D torsionForceVector = forceVersor.scalarMultiply(force / forceVersor.getNorm());


        return torsionForceVector;
    }

}
