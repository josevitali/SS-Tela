package ar.edu.itba.particles;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class StaticParticle extends Particle{
    public StaticParticle(String id, double mass, Vector3D position, double radius) {
        super(id, mass, position, radius);
    }

}
