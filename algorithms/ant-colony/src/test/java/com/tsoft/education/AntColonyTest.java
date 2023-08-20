package com.tsoft.education;

import junit.framework.TestCase;
import org.junit.Test;

public class AntColonyTest extends TestCase {

    @Test
    public void test_1() {
        double[][] weights = new double[][] {
            new double[] { 1.0, 1.0, 1.0 },
            new double[] { 1.0, 1.0, 1.0 },
            new double[] { 1.0, 1.0, 1.0 }
        };

        double[][] distances = new double[][] {
            new double[] { 1.0, 1.0, 1.0 },
            new double[] { 1.0, 1.0, 1.0 },
            new double[] { 1.0, 1.0, 1.0 }
        };

        AntColony colony = new AntColony(weights, distances);
        colony.run_aco_batch(20);
    }

}