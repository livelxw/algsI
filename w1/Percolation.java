public class Percolation {
	private int n;
	private boolean[] flag;
	private WeightedQuickUnionUF uf1;
	private WeightedQuickUnionUF uf2;

	public Percolation(int N) throws IllegalArgumentException// create N-by-N
																// grid, with
																// all sites
																// blocked
	{
		if (N <= 0)
			throw new IllegalArgumentException("IllegalArgumentException");
		n = N;
		uf1 = new WeightedQuickUnionUF(N * N + 2);
		uf2 = new WeightedQuickUnionUF(N * N + 2);
		flag = new boolean[N * N + 2];

		for (int i = 1; i <= N; i++) {
			uf1.union(i, 0);
			uf2.union(i, 0);
			uf1.union(N * N + 1 - i, N * N + 1);
		}

	}

	public void open(int i, int j) throws IndexOutOfBoundsException// open site
																	// (row i,
																	// column j)
																	// if it is
																	// not
	// already
	{
		if (i <= 0 || i > n)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > n)
			throw new IndexOutOfBoundsException("col index j out of bounds");

		flag[(i - 1) * n + j] = true;

		int[] ner = { (i - 1) * n + j + 1, (i - 1) * n + j - 1, (i) * n + j,
				(i - 2) * n + j };

		for (int k = 0; k < 4; k++) {
			if((j==1&&k==1)||(j==n&&k==0))
				continue;
			if (ner[k] >= 0 && ner[k] <= (n * n) && flag[ner[k]]) {
				uf1.union((i - 1) * n + j, ner[k]);
				uf2.union((i - 1) * n + j, ner[k]);
			}
		}
	}

	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException// is
																		// site
																		// (row
																		// i,
																		// column
																		// j)
																		// open?
	{
		if (i <= 0 || i > n)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > n)
			throw new IndexOutOfBoundsException("col index j out of bounds");

		if (!flag[(i - 1) * n + j])
			return false;
		return true;
	}

	public boolean isFull(int i, int j) throws IndexOutOfBoundsException// is
																		// site
																		// (row
																		// i,
																		// column
																		// j)
																		// full?
	{
		if (i <= 0 || i > n)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > n)
			throw new IndexOutOfBoundsException("col index j out of bounds");

		int id = (i - 1) * n + j;

		if (i == 1)
			return isOpen(i, j);

		return uf2.connected(id, 0);
	}

	public boolean percolates() // does the system percolate?
	{
		if (n == 1)
			return isOpen(1, 1);
		if (uf1.connected(n * n + 1, 0))
			return true;
		return false;
	}
}
