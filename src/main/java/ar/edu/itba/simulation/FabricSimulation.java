package ar.edu.itba.simulation;

import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.integrators.Integrator;
import ar.edu.itba.particles.*;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;
import java.util.function.BiFunction;

public class FabricSimulation extends Observable {
    public static Vector3D GRAVITY = new Vector3D(0, -9.8, 0);
    private LinearSpring linearSpring;
    private double dt;
    private double t;
    private Collection<FabricParticle> particles;
    private Integrator integrator;

    public FabricSimulation(Parameters parameters, Collection<Observer> observers) {
        this.t = parameters.initialT;
        this.dt = parameters.dt;
        this.linearSpring = parameters.linearSpring;
        this.integrator = parameters.integrator;
        this.particles = this.generateParticles(parameters.width, parameters.height, parameters.initialState);
        observers.forEach(this::addObserver);
    }

    public Vector3D getAppliedForce(FabricParticle particle) {
        Vector3D totalForce = Vector3D.ZERO;
        for (Particle neighbour: particle.getNeighbours()) {
            totalForce.add(linearSpring.getForce(particle.getPosition(),neighbour.getPosition(),particle.getPreviousPosition(), neighbour.getPreviousPosition(), dt));
        }
        totalForce.add(GRAVITY);
        return totalForce;
    }

    public Particle update(FabricParticle particle) {
        Vector3D[] newVals = integrator.integrate(particle.getPosition(), particle.getVelocity(), particle.getAcceleration(), particle.getPreviousPosition(), dt);
        particle.setPreviousPosition(particle.getPosition());
        particle.setPosition(newVals[0]);
        particle.setVelocity(newVals[1]);
        particle.setForce(Vector3D.ZERO);
        return particle;
    }

    public FabricSimulation simulate(int steps) {
        for (int i = 0; i < steps; i++) {
            for (FabricParticle particle: particles) {
                particle.setForce(getAppliedForce(particle));
                update(particle);
            }
            this.setChanged();
            this.notifyObservers();
        }
        return this;
    }

    private Collection<FabricParticle> generateParticles(int width, int height, BiFunction<Integer, Integer, Particle> initialState) {
        Particle[][] matrix = new FabricParticle[width][height];
        Set<FabricParticle> particles = new HashSet<>(width*height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = initialState.apply(i, j);
            }
        }
        for (int i=1; i<width+1; i++) {
            for (int j=1; j<height+1; j++) {
                if (matrix[i][j] instanceof FabricParticle) {
                    particles.add(((FabricParticle) matrix[i][j])
                            .addNeighbour(matrix[i - 1][j - 1])
                            .addNeighbour(matrix[i][j - 1])
                            .addNeighbour(matrix[i + 1][j - 1])
                            .addNeighbour(matrix[i - 1][j])
                            .addNeighbour(matrix[i + 1][j])
                            .addNeighbour(matrix[i - 1][j + 1])
                            .addNeighbour(matrix[i][j + 1])
                            .addNeighbour(matrix[i + 1][j + 1]));
                }
            }
        }
        return particles;
    }

    public Collection<FabricParticle> getParticles() {
        return particles;
    }

    public double getTime() {
        return t;
    }
}
