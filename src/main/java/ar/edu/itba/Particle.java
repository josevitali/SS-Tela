package ar.edu.itba;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Particle{
    private double mass;
    private Vector3D position;
    private Vector3D velocity;

    public Particle(double mass, Vector3D position) {
        this.mass = mass;
        this.position = position;
        this.velocity = new Vector3D(0, 0, 0);
    }

    public Particle(double mass, Vector3D position, Vector3D velocity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getVelocity() {
        return velocity;
    }
}
