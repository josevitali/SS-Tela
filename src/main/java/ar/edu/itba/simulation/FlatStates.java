package ar.edu.itba.simulation;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.particles.IDGenerator;
import ar.edu.itba.particles.Particle;
import ar.edu.itba.particles.StaticParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class FlatStates {
    double separation;
    double particleMass;
    double particleRadius;
    IDGenerator generator;

    public FlatStates(double separation, double particleMass, double particleRadius, IDGenerator generator) {
        this.separation = separation;
        this.particleMass = particleMass;
        this.particleRadius = particleRadius;
        this.generator = generator;
    }

    public Particle flat1BorderState(int i, int j) {
        if (i == 0) {
            return new StaticParticle(generator.getID(), particleMass, getPos(i, j), particleRadius);
        }
        return new FabricParticle(generator.getID(), particleMass, particleRadius, getPos(i, j));
    }

    private Vector3D getPos(int i, int j) {
        return new Vector3D(i*separation, j*separation, 0);
    }
}
