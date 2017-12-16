package ar.edu.itba.simulation;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.integrators.Integrator;
import ar.edu.itba.particles.Particle;
import ar.edu.itba.springs.TorsionSpring;

import java.util.function.BiFunction;

public class Parameters {
    private int width,height;
    private LinearSpring linearSpring;
    private TorsionSpring torsionSpring;
    private double initialT, dt;
    private BiFunction<Integer, Integer, FabricParticle> initialState;
    private Integrator integrator;

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

    public LinearSpring getLinearSpring() {
        return linearSpring;
    }

    public void setLinearSpring(LinearSpring linearSpring) {
        this.linearSpring = linearSpring;
    }

    public TorsionSpring getTorsionSpring() {
        return torsionSpring;
    }

    public void setTorsionSpring(TorsionSpring torsionSpring) {
        this.torsionSpring = torsionSpring;
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
}
