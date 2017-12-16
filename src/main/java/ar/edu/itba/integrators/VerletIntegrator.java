package ar.edu.itba.integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VerletIntegrator implements Integrator{

    public Vector3D[] integrate(Vector3D position, Vector3D velocity, Vector3D acceleration, Vector3D previousPosition, double dt) {

        Vector3D[] newVals = new Vector3D[2];

        final double posX = 2 * position.getX() - previousPosition.getX() + acceleration.getX() * dt*dt;
        final double posY = 2 * position.getY() - previousPosition.getY() + acceleration.getY() * dt*dt;
        final double posZ = 2 * position.getZ() - previousPosition.getZ() + acceleration.getZ() * dt*dt;
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
