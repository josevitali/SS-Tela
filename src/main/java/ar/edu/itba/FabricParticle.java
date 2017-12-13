package ar.edu.itba;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Collection;
import java.util.stream.Collector;

import static ar.edu.itba.FabricSimulation.GRAVITY;

public class FabricParticle extends Particle{
    private Collection<Particle> neighbours;

    public FabricParticle(double mass, Vector3D position, Collection<Particle> neighbours) {
        super(mass, position);
        this.neighbours = neighbours;
    }

    public Vector3D getAppliedForce(Collector<Particle, Vector3D, Vector3D> collector) {
        //TODO:collect jajaj
        return null;
    }

    private void setPosition(Vector3D newPos) {
        this.position = newPos;
    }

    private void setVelocity(Vector3D newVel) {
        this.velocity = newVel;
    }

    private void setForce(Vector3D newForce) {
        this.force = force;
    }
}
