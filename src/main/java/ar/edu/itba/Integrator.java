package ar.edu.itba;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Integrator {
    Vector3D[] integrate(Vector3D position, Vector3D velocity, Vector3D acceleration, double dt);
}
