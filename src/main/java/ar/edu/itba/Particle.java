package ar.edu.itba;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Particle{
    protected double mass;
    protected Vector3D position;
    protected Vector3D velocity;
    protected Vector3D force;

    public Particle(double mass, Vector3D position) {
        this.mass = mass;
        this.position = position;
        this.velocity = new Vector3D(0, 0, 0);
        this.force = new Vector3D(0, 0, 0);
    }

    public Particle(double mass, Vector3D position, Vector3D velocity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.force = new Vector3D(0, 0, 0);
    }

    public Particle(double mass, Vector3D position, Vector3D velocity, Vector3D force) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.force = force;
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

    public Vector3D getForce() {
        return force;
    }
}
