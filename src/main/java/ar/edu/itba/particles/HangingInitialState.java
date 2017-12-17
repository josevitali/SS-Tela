package ar.edu.itba.particles;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.function.BiFunction;

public class HangingInitialState implements BiFunction<Integer, Integer, FabricParticle> {

    private final double distance;
    private IDGenerator idGenerator;
    private final double mass;
    private final double radius;

    public HangingInitialState(double distance, double mass, double radius) {
        this.distance = distance;
        this.mass = mass;
        this.radius = radius;
        this.idGenerator = new LongIDGenerator();
    }

    @Override
    public FabricParticle apply(Integer i, Integer j) {
        FabricParticle fabricParticle = new FabricParticle(this.idGenerator.getID(), this.mass, this.radius, new Vector3D(i * distance, 0, -j * distance));
        if(j == 0) {
            fabricParticle.setUnmovable(true);
        }
        if(i == 19 && j == 19) {
            fabricParticle.addForce(new Vector3D(0, 10000000.0, 0));
        }
        if(i == 0 && j == 19) {
            fabricParticle.addForce(new Vector3D(0, -10000000.0, 0));
        }
        return fabricParticle;
    }
}
