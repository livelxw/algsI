import java.util.Arrays;

public class Brute {

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] pArray =  new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			pArray[i] = new Point(x, y);
			pArray[i].draw();
		}
		
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		
		Arrays.sort(pArray);
		for (int a = 0; a < N; a++)
			for (int b = a + 1; b < N; b++)
				for (int c = b + 1; c < N; c++)
					for (int d = c + 1; d < N; d++)
						if (pArray[a].slopeTo(pArray[b]) == pArray[a]
								.slopeTo(pArray[c])
								&& pArray[a].slopeTo(pArray[b]) == pArray[a]
										.slopeTo(pArray[d])) {
							StdOut.println(pArray[a].toString() + " -> "
									+ pArray[b].toString() + " -> "
									+ pArray[c].toString() + " -> "
									+ pArray[d].toString());
							pArray[a].drawTo(pArray[d]);
						}
		StdDraw.show(0);
	}

}
