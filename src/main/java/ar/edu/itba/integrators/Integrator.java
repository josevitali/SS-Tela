package ar.edu.itba.integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Integrator {
    Vector3D[] integrate(Vector3D position, Vector3D velocity, Vector3D acceleration, Vector3D previousPos, double dt);
}
