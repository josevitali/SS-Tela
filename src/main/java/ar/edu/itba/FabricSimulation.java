package ar.edu.itba;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.stream.Collector;

public class FabricSimulation {
    public static Vector3D GRAVITY = new Vector3D(0, -9.8, 0);
    public Spring spring;

    public FabricSimulation() {
        this.spring = new Spring(1, 1, 1);

    }
}
