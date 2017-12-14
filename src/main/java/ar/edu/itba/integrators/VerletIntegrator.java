package ar.edu.itba.integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VerletIntegrator implements Integrator{

    public Vector3D[] integrate(Vector3D position, Vector3D velocity, Vector3D previousPosition, Vector3D acceleration, double dt) {
        Vector3D[] newVals = new Vector3D[2];
        newVals[0] = position
                .scalarMultiply(2)
                .subtract(previousPosition)
                .add(acceleration.scalarMultiply(dt*dt));
        newVals[1] = newVals[0]
                .subtract(previousPosition)
                .scalarMultiply(1.0/(dt*dt));
        return newVals;
    }
}
