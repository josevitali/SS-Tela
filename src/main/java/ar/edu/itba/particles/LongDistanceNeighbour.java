package ar.edu.itba.particles;


public class LongDistanceNeighbour {

    private final Particle middleNeighbour;
    private final Particle oppositeNeighbour;

    public LongDistanceNeighbour(Particle middleNeighbour, Particle oppositeNeighbour) {
        this.middleNeighbour = middleNeighbour;
        this.oppositeNeighbour = oppositeNeighbour;
    }

    public Particle getMiddleNeighbour() {
        return middleNeighbour;
    }

    public Particle getOppositeNeighbour() {
        return oppositeNeighbour;
    }
}
