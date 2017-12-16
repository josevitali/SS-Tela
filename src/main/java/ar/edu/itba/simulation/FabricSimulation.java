package ar.edu.itba.simulation;

import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.integrators.Integrator;
import ar.edu.itba.particles.*;
import ar.edu.itba.springs.TorsionSpring;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;
import java.util.function.BiFunction;

public class FabricSimulation extends Observable {
    public static Vector3D GRAVITY = new Vector3D(0, -9.8, 0);
    private LinearSpring linearSpring;
    private TorsionSpring torsionSpring;
    private double dt;
    private double t;
    private Collection<FabricParticle> particles;
    private Integrator integrator;

    public FabricSimulation(Parameters parameters, Collection<Observer> observers) {
        this.t = parameters.initialT;
        this.dt = parameters.dt;
        this.linearSpring = parameters.linearSpring;
        this.torsionSpring = parameters.torsionSpring;
        this.integrator = parameters.integrator;
        this.particles = this.generateParticles(parameters.width, parameters.height, parameters.initialState);
        observers.forEach(this::addObserver);
    }

    public Vector3D getAppliedForce(FabricParticle particle) {
        Vector3D totalForce = Vector3D.ZERO;
        for (Particle neighbour: particle.getNeighbours()) {
            totalForce.add(linearSpring.getForce(particle.getPosition(),neighbour.getPosition(),particle.getPreviousPosition(), neighbour.getPreviousPosition(), dt));
        }
        for (LongDistanceNeighbour longNeighbour : particle.getLongDistanceNeighbours()) {
            Particle middle = longNeighbour.getMiddleNeighbour();
            Particle opposite = longNeighbour.getOppositeNeighbour();
            totalForce.add(torsionSpring.getForce(particle.getPosition(), opposite.getPosition(), middle.getPosition(), particle.getPreviousPosition(), opposite.getPreviousPosition(), dt));
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

        final int[][] offsets = new int[][]{ {-1, 1}, {0, 1}, {-1, 0}, {-1, -1}, {1, 0}, {0, -1}, {1, -1} };
        final int[][] longOffsets = new int[][]{ {-2, 0}, {2, 0}, {0, -2}, {0, 2} };

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (matrix[i][j] instanceof FabricParticle) {
                    FabricParticle particle = (FabricParticle) matrix[i][j];

                    for (int[] offset : offsets) {
                        final int x = j + offset[0];
                        final int y = i + offset[1];
                        if(! (x < 0 || y < 0 || x >= width || y >= height)) {
                            particle.addNeighbour(matrix[y][x]);
                        }
                    }

                    for (int[] longOffset : longOffsets) {
                        final int x = j + longOffset[0];
                        final int y = i + longOffset[1];
                        if(! (x < 0 || y < 0 || x >= width || y >= height)) {
                            int l = 0;
                            int m = 0;
                            if(longOffset[0] > 0) l = x - 1;
                            if(longOffset[0] < 0) l = x + 1;
                            if(longOffset[1] > 0) m = y - 1;
                            if(longOffset[1] < 0) m = y + 1;
                            LongDistanceNeighbour neighbour = new LongDistanceNeighbour(matrix[m][l], matrix[y][x]);
                            particle.addLongDistanceNeighbour(neighbour);
                        }
                    }
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
