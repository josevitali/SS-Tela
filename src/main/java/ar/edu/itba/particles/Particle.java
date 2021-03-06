package ar.edu.itba.particles;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Particle{
    private String id;
    protected double mass;
    protected Vector3D position;
    protected Vector3D previousPosition;
    protected Vector3D velocity;
    protected Vector3D force;

    public Particle(String id, double mass, Vector3D position) {
        this.mass = mass;
        this.position = position;
        this.previousPosition = this.position;
        this.velocity = Vector3D.ZERO;
        this.force = Vector3D.ZERO;
    }

    public Particle(String id, double mass, Vector3D position, Vector3D velocity) {
        this.mass = mass;
        this.position = position;
        this.previousPosition = position;
        this.velocity = velocity;
        this.force = Vector3D.ZERO;
    }

    public Particle(String id, double mass, Vector3D position, Vector3D velocity, Vector3D force) {
        this.mass = mass;
        this.position = position;
        this.previousPosition = position;
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

    public Vector3D getPreviousPosition() {
        return previousPosition;
    }

    public Vector3D getAcceleration() {
        return force.scalarMultiply(1.0/mass);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        if (Double.compare(particle.mass, mass) != 0) return false;
        if (id != null ? !id.equals(particle.id) : particle.id != null) return false;
        if (position != null ? !position.equals(particle.position) : particle.position != null) return false;
        if (previousPosition != null ? !previousPosition.equals(particle.previousPosition) : particle.previousPosition != null)
            return false;
        if (velocity != null ? !velocity.equals(particle.velocity) : particle.velocity != null) return false;
        return force != null ? force.equals(particle.force) : particle.force == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(mass);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (previousPosition != null ? previousPosition.hashCode() : 0);
        result = 31 * result + (velocity != null ? velocity.hashCode() : 0);
        result = 31 * result + (force != null ? force.hashCode() : 0);
        return result;
    }
}
