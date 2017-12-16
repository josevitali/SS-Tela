package ar.edu.itba.simulation;

import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.integrators.Integrator;
import ar.edu.itba.particles.Particle;
import ar.edu.itba.springs.TorsionSpring;

import java.util.function.BiFunction;

public class Parameters {
    int width,height;
    LinearSpring linearSpring;
    TorsionSpring torsionSpring;
    double initialT, dt;
    BiFunction<Integer, Integer, Particle> initialState;
    Integrator integrator;

}
