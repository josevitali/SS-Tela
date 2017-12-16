package ar.edu.itba.particles;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Collection;
import java.util.HashSet;

public class FabricParticle extends Particle{
    private Collection<Particle> neighbours;
    private Collection<LongDistanceNeighbour> longDistanceNeighbours;
    private double radius;

    public FabricParticle(String id, double mass, double radius, Vector3D position) {
        super(id, mass, position);
        this.neighbours = new HashSet<>();
    }

    public FabricParticle(String id, double mass, double radius, Vector3D position, Collection<Particle> neighbours, Collection<LongDistanceNeighbour> longDistanceNeighbours) {
        super(id, mass, position);
        this.neighbours = neighbours;
        this.longDistanceNeighbours = longDistanceNeighbours;
        this.radius = radius;
    }

    public Collection<Particle> getNeighbours() {
        return neighbours;
    }

    public Collection<LongDistanceNeighbour> getLongDistanceNeighbours() {
        return longDistanceNeighbours;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    public void setForce(Vector3D force) {
        this.force = force;
    }

    public void setPreviousPosition(Vector3D previousPosition) {
        this.previousPosition = previousPosition;
    }

    public FabricParticle addNeighbour(Particle particle) {
        this.neighbours.add(particle);
        return this;
    }

    public FabricParticle addLongDistanceNeighbour(LongDistanceNeighbour neighbour) {
        this.longDistanceNeighbours.add(neighbour);
        return this;
    }

    public double getRadius() {
        return radius;
    }
}
