package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int gridwith;
    private int T;
    private double[] threshold;
    private Percolation trial;
    private double area; // For conversion results of threshold to double.

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.gridwith = N;
        this.T = T;
        threshold = new double[T];
        area = gridwith * gridwith;

        for (int i = 0; i < T; i++) {
            trial = pf.make(gridwith);
            while (!trial.percolates()) {
                int row = StdRandom.uniform(gridwith);
                int col = StdRandom.uniform(gridwith);
                trial.open(row, col);
            }
            threshold[i] = trial.numberOfOpenSites() / area;
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
