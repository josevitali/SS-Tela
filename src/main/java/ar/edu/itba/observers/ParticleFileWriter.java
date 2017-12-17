package ar.edu.itba.observers;

import ar.edu.itba.particles.FabricParticle;
import ar.edu.itba.particles.Particle;
import ar.edu.itba.simulation.FabricSimulation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

public class ParticleFileWriter implements Observer {
    private BufferedWriter positionsWriter;
    private BufferedWriter energiesWriter;
    private int index;

    public ParticleFileWriter(String positionsFilename, String energiesFilename) throws  IOException{
        positionsWriter = new BufferedWriter(new FileWriter(positionsFilename));
        energiesWriter = new BufferedWriter(new FileWriter(energiesFilename));
        index = 0;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof FabricSimulation) {
            FabricSimulation sim = (FabricSimulation) observable;
            Collection<FabricParticle> particles = sim.getParticles();
            double kineticEnergy = 0.0;
            double potentialEnergy = 0.0;
            try {
                positionsWriter.write(String.format("%d\n%d\n", particles.size(), index++));

                for (FabricParticle particle : particles) {
                    positionsWriter.write(formatParticle(particle));
                    kineticEnergy+= particle.getKineticEnergy();
                    potentialEnergy+= particle.getPotentialEnergy();
                }
                kineticEnergy/= particles.size();
                potentialEnergy/= particles.size();

                //System.out.println(String.format("%f, %f", kineticEnergy, potentialEnergy));
                energiesWriter.write(String.format("%f, %f\n", kineticEnergy, potentialEnergy));

            } catch (IOException e) {
                System.out.println("Write operation failed");
            }
        }
    }

    private static String formatParticle(final FabricParticle p) {

        Vector3D pos = p.getPosition();
        Vector3D vel = p.getVelocity();
        // id posX posY mass radius
        double velocityMagnitude = vel.getNorm();

        return String.format("%s %f %f %f %f %f %f %f %f\n", p.getId(), pos.getX(), pos.getY(), pos.getZ(), vel.getX(), vel.getY(), vel.getZ(), p.getRadius(), velocityMagnitude);
    }
}
