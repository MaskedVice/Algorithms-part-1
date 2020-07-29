import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final int n;
    private final int trials;
    private double[] result;       // record the fraction of open sites in each trial
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        result = new double[trials];
        this.run();                    // calculate things needed after creating object
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    private void run() {
        // calculate mean
        for (int i = 0; i < trials; i++) {
            result[i] = 1.0 * calOnce() / n / n;
        }
        mean = StdStats.mean(result);

        // calculate stddev
        stddev = StdStats.stddev(result);

        // calculate Lo and Hi
        double deviation = CONFIDENCE * stddev() / Math.sqrt(trials);
        confidenceLo = mean() - deviation;
        confidenceHi = mean() + deviation;
    }

    // calulate the number of open sites when the system percolates in one trial
    private int calOnce() {
        Percolation p = new Percolation(n);
        while (true) {
            int x = StdRandom.uniform(n) + 1;
            int y = StdRandom.uniform(n) + 1;
            // each site can be opened only once
            if (p.isOpen(x, y)) {
                continue;
            }
            p.open(x, y);
            if (p.percolates()) {
                break;
            }
        }
        return p.numberOfOpenSites();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean);
        StdOut.println("stddev                  = " + ps.stddev);
        StdOut.println("95% confidence interval = [" + ps.confidenceLo + ", " + ps.confidenceHi + "]");
    }
}
