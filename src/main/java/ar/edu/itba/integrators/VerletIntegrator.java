package ar.edu.itba.integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VerletIntegrator implements Integrator{

    public Vector3D[] integrate(Vector3D position, Vector3D velocity, Vector3D force, Vector3D previousPosition, double dt, double mass) {

        Vector3D[] newVals = new Vector3D[2];

        final double posX = 2 * position.getX() - previousPosition.getX() + force.getX() * dt*dt / mass;
        final double posY = 2 * position.getY() - previousPosition.getY() + force.getY() * dt*dt / mass;
        final double posZ = 2 * position.getZ() - previousPosition.getZ() + force.getZ() * dt*dt / mass;
        final Vector3D pos = new Vector3D(posX, posY, posZ);

        final double velX = (posX - previousPosition.getX()) / (2.0 * dt);
        final double velY = (posY - previousPosition.getY()) / (2.0 * dt);
        final double velZ = (posZ - previousPosition.getZ()) / (2.0 * dt);

        final Vector3D vel = new Vector3D(velX, velY, velZ);

        newVals[0] = pos;
        newVals[1] = vel;

        /*newVals[0] = position
                .scalarMultiply(2)
                .subtract(previousPosition)
                .add(acceleration.scalarMultiply(dt*dt));
        newVals[1] = newVals[0]
                .subtract(previousPosition)
                .scalarMultiply(1.0/(2.0*dt));*/


        return newVals;
    }
}
