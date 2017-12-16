package ar.edu.itba;


import ar.edu.itba.integrators.VerletIntegrator;
import ar.edu.itba.observers.ParticleFileWriter;
import ar.edu.itba.particles.RegularInitialState;
import ar.edu.itba.simulation.FabricSimulation;
import ar.edu.itba.simulation.Parameters;
import ar.edu.itba.springs.LinearSpring;
import ar.edu.itba.springs.TorsionSpring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class App  {

    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;
    public static final double RADIUS = 1.0;
    public static final double MASS = 1.0;
    public static final double PARTICLE_DISTANCE = 3;
    public static final double K = 1000;
    public static final double TORSION_K = K / 10;
    public static final double REST_DISTANCE = 3;
    public static final double TORSION_NATURAL_ANGLE = 0;
    public static final double DT = 0.00001;




    public static void main(String[] args) {

        List<Observer> observers = new ArrayList<>();

        try {
            ParticleFileWriter particleFileWriter = new ParticleFileWriter("out.txt");
            observers.add(particleFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parameters parameters = new Parameters();
        parameters.setDt(DT);
        parameters.setHeight(HEIGHT);
        parameters.setWidth(WIDTH);
        parameters.setInitialState(new RegularInitialState(PARTICLE_DISTANCE, MASS, RADIUS));
        parameters.setInitialT(0);
        parameters.setIntegrator(new VerletIntegrator());
        parameters.setLinearSpring(new LinearSpring(K, REST_DISTANCE));
        parameters.setTorsionSpring(new TorsionSpring(TORSION_K, TORSION_NATURAL_ANGLE));

        FabricSimulation fabricSimulation = new FabricSimulation(parameters, observers);

        fabricSimulation.simulate(100000);


    }
}
