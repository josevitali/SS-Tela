package ar.edu.itba.simulation;

import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.integrators.Integrator;
import ar.edu.itba.particles.*;
import ar.edu.itba.springs.Spring;
import ar.edu.itba.springs.TorsionSpring;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;
import java.util.function.BiFunction;

public class FabricSimulation extends Observable {
    public Vector3D GRAVITY;
    private LinearSpring linearSpring;
    private TorsionSpring torsionSpring;
    private double dt;
    private double t;
    private Collection<FabricParticle> particles;
    private Integrator integrator;
    private Collection<Spring> springs;
    private FabricParticle[][] matrix;

    public FabricSimulation(Parameters parameters, Collection<Observer> observers) {
        this.GRAVITY = new Vector3D(0, 0, - parameters.getMass() * 9.8);
        this.t = parameters.getInitialT();
        this.dt = parameters.getDt();
        this.linearSpring = parameters.getLinearSpring();
        this.torsionSpring = parameters.getTorsionSpring();
        this.integrator = parameters.getIntegrator();
        this.particles = this.generateParticles(parameters.getWidth(), parameters.getHeight(), parameters.getInitialState());
        this.springs = this.generateSprings(parameters.getWidth(), parameters.getHeight(), parameters.getLinearK(), parameters.getTorsionK(), parameters.getRestDistance(), parameters.getTorsionNaturalAngle());
        observers.forEach(this::addObserver);
    }

    public Vector3D getAppliedForce(FabricParticle particle) {
        Vector3D totalForce = Vector3D.ZERO;
        /*for (Particle neighbour: particle.getNeighbours()) {
            totalForce = totalForce.add(linearSpring.getForce(particle, neighbour, dt));
        }*/
        /*for (LongDistanceNeighbour longNeighbour : particle.getLongDistanceNeighbours()) {
            Particle middle = longNeighbour.getMiddleNeighbour();
            Particle opposite = longNeighbour.getOppositeNeighbour();
            totalForce = totalForce.add(torsionSpring.getForce(particle, opposite, middle, dt));
        }*/
        totalForce = totalForce.add(GRAVITY);
        return totalForce;
    }

    public Particle update(FabricParticle particle) {
        Vector3D[] newVals = integrator.integrate(particle.getPosition(), particle.getVelocity(), particle.getForce(), particle.getPreviousPosition(), dt, particle.getMass());
        particle.setPreviousPosition(particle.getPosition());
        particle.setPosition(newVals[0]);
        particle.setVelocity(newVals[1]);
        particle.setForce(Vector3D.ZERO);
        return particle;
    }

    public FabricSimulation simulate(int steps) {
        this.setChanged();
        this.notifyObservers();

        for (int i = 0; i < steps; i++) {

            for(Spring spring : springs) {
                spring.apply();
            }

            double dampingCoeficient = 0.025;
            double eps = Math.pow(10, -5);

            for (FabricParticle particle: particles) {
                if(!particle.isUnmovable()) {

                    final double velocity = particle.getVelocity().getNorm();

                    if (! (velocity < eps)) {
                        final Vector3D dampingForce = particle.getVelocity().scalarMultiply(-dampingCoeficient);
                        particle.addForce(dampingForce);
                    }




                    particle.addForce(GRAVITY);
                    update(particle);
                }
            }
            if(i % 1667 == 0) {
                this.setChanged();
                this.notifyObservers();
            }
        }
        return this;
    }

    private Collection<FabricParticle> generateParticles(int width, int height, BiFunction<Integer, Integer, FabricParticle> initialState) {
        this.matrix = new FabricParticle[width][height];
        Set<FabricParticle> particles = new HashSet<>(width*height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = initialState.apply(i, j);
            }
        }

        final int[][] offsets = new int[][]{ {-1, 1}, {0, 1}, {-1, 0}, {-1, -1}, {1, 0}, {0, -1}, {1, -1}, {1, 1} };
        final int[][] longOffsets = new int[][]{ {-2, 0}, {2, 0}, {0, -2}, {0, 2} };

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (matrix[i][j] instanceof FabricParticle) {
                    FabricParticle particle = matrix[i][j];

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
                            int l = x;
                            int m = y;
                            if(longOffset[0] > 0) l = x - 1;
                            if(longOffset[0] < 0) l = x + 1;
                            if(longOffset[1] > 0) m = y - 1;
                            if(longOffset[1] < 0) m = y + 1;
                            LongDistanceNeighbour neighbour = new LongDistanceNeighbour(matrix[m][l], matrix[y][x]);
                            particle.addLongDistanceNeighbour(neighbour);
                        }
                    }
                    /*if(particle.getId().equals("1") || particle.getId().equals("20")) {
                        particle.setUnmovable(true);
                    }*/
                    particles.add(particle);
                }
            }
        }
        return particles;
    }

    private Collection<Spring> generateSprings(int width, int height, double linearK, double torsionK, double restDistance, double torsionNaturalAngle) {

        Collection<Spring> springs = new HashSet<>();

        final int[][] offsets = new int[][]{ {-1, 1}, {0, 1}, {-1, 0}, {-1, -1} };
        final int[][] longOffsets = new int[][]{ {2, 0}, {0, 2} };

        for (int i=0; i<width; i++) {
            for (int j = 0; j < height; j++) {

                FabricParticle particle = matrix[i][j];

                for (int[] offset : offsets) {
                    final int x = j + offset[0];
                    final int y = i + offset[1];
                    if(! (x < 0 || y < 0 || x >= width || y >= height)) {
                        FabricParticle otherParticle = matrix[y][x];

                        final double distance = otherParticle.getPosition().subtract(particle.getPosition()).getNorm();

                        springs.add(new LinearSpring(particle, otherParticle, linearK, distance, dt));
                    }
                }

                /*for (int[] longOffset : longOffsets) {
                    final int x = j + longOffset[0];
                    final int y = i + longOffset[1];
                    if(! (x < 0 || y < 0 || x >= width || y >= height)) {
                        int l = x;
                        int m = y;
                        if(longOffset[0] > 0) l = x - 1;
                        if(longOffset[0] < 0) l = x + 1;
                        if(longOffset[1] > 0) m = y - 1;
                        if(longOffset[1] < 0) m = y + 1;
                        springs.add(new TorsionSpring(particle, matrix[y][x], matrix[m][l], torsionK, torsionNaturalAngle, dt));
                        //LongDistanceNeighbour neighbour = new LongDistanceNeighbour(matrix[m][l], matrix[y][x]);
                        //particle.addLongDistanceNeighbour(neighbour);
                    }
                }*/

            }
        }

        return springs;
    }

    public Collection<FabricParticle> getParticles() {
        return particles;
    }

    public double getTime() {
        return t;
    }
}
