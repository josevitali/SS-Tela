package ar.edu.itba.particles;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Collection;
import java.util.HashSet;

public class FabricParticle extends Particle{
    private Collection<FabricParticle> neighbours;
    private Collection<LongDistanceNeighbour> longDistanceNeighbours;
    private boolean unmovable = false;

    public FabricParticle(String id, double mass, double radius, Vector3D position) {
        super(id, mass, position, radius);
        this.neighbours = new HashSet<>();
        this.longDistanceNeighbours = new HashSet<>();
    }

    public FabricParticle(String id, double mass, double radius, Vector3D position, Collection<FabricParticle> neighbours, Collection<LongDistanceNeighbour> longDistanceNeighbours) {
        super(id, mass, position, radius);
        this.neighbours = neighbours;
        this.longDistanceNeighbours = longDistanceNeighbours;
    }

    public Collection<FabricParticle> getNeighbours() {
        return neighbours;
    }

    public Collection<LongDistanceNeighbour> getLongDistanceNeighbours() {
        return longDistanceNeighbours;
    }

    public boolean isUnmovable() {
        return unmovable;
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

    public void setUnmovable(boolean unmovable) {
        this.unmovable = unmovable;
    }

    public FabricParticle addNeighbour(FabricParticle particle) {
        this.neighbours.add(particle);
        return this;
    }

    public FabricParticle addLongDistanceNeighbour(LongDistanceNeighbour neighbour) {
        this.longDistanceNeighbours.add(neighbour);
        return this;
    }

}
