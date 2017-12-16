package ar.edu.itba.particles;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.function.BiFunction;

public class RegularInitialState implements BiFunction<Integer, Integer, FabricParticle> {

    private final double distance;
    private IDGenerator idGenerator;
    private final double mass;
    private final double radius;

    public RegularInitialState(double distance, double mass, double radius) {
        this.distance = distance;
        this.mass = mass;
        this.radius = radius;
        this.idGenerator = new LongIDGenerator();
    }

    @Override
    public FabricParticle apply(Integer i, Integer j) {
        return new FabricParticle(this.idGenerator.getID(), this.mass, this.radius, new Vector3D(i * distance, j * distance, 0));
    }
}
