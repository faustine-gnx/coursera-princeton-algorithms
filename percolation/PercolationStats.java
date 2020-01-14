/* *****************************************************************************
 *  Name: Faustine Ginoux
 *  Date: 14-01-2020
 *  Description: Coursera - Algo part I - Percolation assignment - PercolationStats class
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // private int n;
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] pEstim;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("The size and number of trials must be positive!");
        }
        // this.n = n;
        this.trials = trials;
        this.pEstim = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perco = new Percolation(n);
            while (!perco.percolates()) {
                perco.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            this.pEstim[i] = (double) perco.numberOfOpenSites() / (n * n);
            // System.out.println(this.pEstim[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.pEstim);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.pEstim);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); // StdIn.readInt();
        int trials = Integer.parseInt(args[1]); // StdIn.readInt();
        PercolationStats percoStats = new PercolationStats(n, trials);
        System.out.println("mean = " + percoStats.mean());
        System.out.println("stddev = " + percoStats.stddev());
        System.out.println(
                "95% CI = [" + percoStats.confidenceLo() + ", " + percoStats.confidenceHi() + "]");
    }

}
