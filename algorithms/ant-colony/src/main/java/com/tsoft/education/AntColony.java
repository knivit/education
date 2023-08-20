package com.tsoft.education;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntColony {

    Random rng = new Random();
    int num_nodes;
    double[][] weights;
    double[][] distances;
    boolean running = false;
    double alpha = 1.0;             // pheromone weight
    double beta = 2.0;              // greedy weight

    public AntColony(double[][] weights, double[][] distances) {
        this.weights = weights;
        this.distances = distances;
        this.num_nodes = weights.length;
    }

    public double run_aco_batch(int batch_size) {
        running = false;

        // Evaporate
        for (int i = 0; i < num_nodes; i ++) {
            for (int j = 0; j < num_nodes; j ++) {
                weights[i][j] *= 0.999;
            }
        }

        double[][] new_weights = copy(weights);

        // Run the whole batch
        double best_len = 1000000000;
        for (int n = 0; n < batch_size; n ++) {
            Path ret = get_random_path_from(rng.nextInt(num_nodes));
            List<Integer> path = ret.path;
            double l = ret.dist;

            if (l < best_len) {
                best_len = l;
            }

            double diff = l - best_len + 0.05;
            double w = 0.01 / diff;

            for (int i = 0; i <= num_nodes; i ++) {
                int idx1 = path.get(i % num_nodes);
                int idx2 = path.get((i + 1) % num_nodes);
                new_weights[idx1][idx2] += w;
                new_weights[idx2][idx1] += w;
            }
        }

        // Update the weights after normalizing
        for (int i = 0; i < num_nodes; i ++) {
            double n_sum = 0.0;
            for (int j = 0; j < num_nodes; j ++) {
                if (i != j) {
                    n_sum += new_weights[i][j];
                }
            }

            for (int j = 0; j < num_nodes; j ++) {
                // multiplying by 2 since every node has two neighbors eventually
                weights[i][j] = 2 * new_weights[i][j] / n_sum;
            }
        }

        running = true;
        return best_len;
    }

    private double get_transition_probability(int idx1, int idx2) {
        return Math.pow(weights[idx1][idx2], alpha) * Math.pow(distances[idx1][idx2], -beta);
    }

    private class Path {
        private List<Integer> path;
        private double dist;

        private Path(List<Integer> path, double dist) {
            this.path = path;
            this.dist = dist;
        }
    }

    private Path get_random_path_from(int idx) {
        List<Integer> path = new ArrayList<>();
        double dist = 0.0;
        path.add(idx);
        int curr_idx = idx;
        while (path.size() < num_nodes) {
            double n_sum = 0.0;
            List<Integer> possible_next = new ArrayList<>();
            for (int n = 0; n < num_nodes; n ++) {
                // already visited
                if (path.contains(n)) {
                    continue;
                }

                n_sum += get_transition_probability(curr_idx, n);
                possible_next.add(n);
            }

            double r = rng.nextDouble(n_sum);
            double x = 0.0;
            for (int n = 0; n < possible_next.size(); n ++) {
                int nn = possible_next.get(n);
                x += get_transition_probability(curr_idx, nn);
                if (r <= x) {
                    dist += distances[curr_idx][nn];
                    curr_idx = nn;
                    path.add(nn);
                    break;
                }
            }
        }

        dist += distances[curr_idx][idx];
        return new Path(path, dist);
    }

    private double[][] copy(double[][] src) {
        double[][] dst = new double[src.length][];
        for (int i = 0; i < src.length; i ++) {
            dst[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dst;
    }
}
