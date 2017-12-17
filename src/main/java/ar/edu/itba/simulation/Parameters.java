package ar.edu.itba.simulation;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.integrators.Integrator;
import ar.edu.itba.particles.Particle;
import ar.edu.itba.springs.TorsionSpring;

import java.util.function.BiFunction;

public class Parameters {
    private int width,height;
    private double initialT, dt;
    private BiFunction<Integer, Integer, FabricParticle> initialState;
    private Integrator integrator;
    private double linearK;
    private double torsionK;
    private double restDistance;
    private double torsionNaturalAngle;
    private double mass;
    private double dampingCoeficient;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getInitialT() {
        return initialT;
    }

    public void setInitialT(double initialT) {
        this.initialT = initialT;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public BiFunction<Integer, Integer, FabricParticle> getInitialState() {
        return initialState;
    }

    public void setInitialState(BiFunction<Integer, Integer, FabricParticle> initialState) {
        this.initialState = initialState;
    }

    public Integrator getIntegrator() {
        return integrator;
    }

    public void setIntegrator(Integrator integrator) {
        this.integrator = integrator;
    }

    public double getLinearK() {
        return linearK;
    }

    public void setLinearK(double linearK) {
        this.linearK = linearK;
    }

    public double getTorsionK() {
        return torsionK;
    }

    public void setTorsionK(double torsionK) {
        this.torsionK = torsionK;
    }

    public double getRestDistance() {
        return restDistance;
    }

    public void setRestDistance(double restDistance) {
        this.restDistance = restDistance;
    }

    public double getTorsionNaturalAngle() {
        return torsionNaturalAngle;
    }

    public void setTorsionNaturalAngle(double torsionNaturalAngle) {
        this.torsionNaturalAngle = torsionNaturalAngle;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getDampingCoeficient() {
        return dampingCoeficient;
    }

    public void setDampingCoeficient(double dampingCoeficient) {
        this.dampingCoeficient = dampingCoeficient;
    }
}
