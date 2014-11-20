public class PercolationStats {
	private int t;
	private int tmpx;
	private double[] x;
	private double miu;
	private double sigema;

	public PercolationStats(int N, int T) throws IllegalArgumentException// perform
																			// T
																			// independent
																			// computational
	// experiments on an N-by-N grid
	{
		if (N <= 0)
			throw new IllegalArgumentException("N out of bounds");
		if (T <= 0)
			throw new IllegalArgumentException("T out of bounds");
		t = T;
		x = new double[T];

		for (int i = 0; i < T; i++) {
			Percolation per = new Percolation(N);
			miu = 0;
			sigema = 0;
			tmpx = 0;
			while (!per.percolates()) {
				int curI = StdRandom.uniform(1, N + 1);
				int curJ = StdRandom.uniform(1, N + 1);
				if (!per.isOpen(curI, curJ)) {
					per.open(curI, curJ);
					tmpx++;
				}
			}

			x[i] = (double) tmpx / (N * N);
		}
	}

	public double mean() // sample mean of percolation threshold
	{
		return miu = StdStats.mean(x);
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		return sigema = StdStats.stddev(x);
	}

	public double confidenceLo() // returns lower bound of the 95% confidence
									// interval
	{
		return (miu - ((1.96 * sigema) / Math.sqrt(t)));
	}

	public double confidenceHi() {
		return (miu + ((1.96 * sigema) / Math.sqrt(t)));
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		int T = StdIn.readInt();

		PercolationStats perStats = new PercolationStats(N, T);
		StdOut.println("mean                    = " + perStats.mean());
		StdOut.println("stddev                  = " + perStats.stddev());
		StdOut.println("95% confidence interval = " + perStats.confidenceLo()
				+ ", " + perStats.confidenceHi());

	}

}
