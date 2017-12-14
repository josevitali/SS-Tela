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
    private BufferedWriter writer;

    public ParticleFileWriter(String filename) throws  IOException{
        writer = new BufferedWriter(new FileWriter(filename));
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof FabricSimulation) {
            FabricSimulation sim = (FabricSimulation) observable;
            Collection<FabricParticle> particles = sim.getParticles();
            try {
                writer.write(String.format("%d\nTime = %g\n", particles.size(), sim.getTime()));

                for (FabricParticle particle : particles) {
                    writer.write(formatParticle(particle));
                }

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
