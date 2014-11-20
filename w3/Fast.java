import java.util.*;

public class Fast {

	public static void main(String[] args) {
		long pre = System.currentTimeMillis();
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] pArrayOrigin = new Point[N];
		Point[] pArray = new Point[N];

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			pArrayOrigin[i] = new Point(x, y);
			pArrayOrigin[i].draw();
		}
		
		Merge.sort(pArrayOrigin);
		for (int i = 0; i < N; i++)
			pArray[i] = pArrayOrigin[i];

		for (int j = 0; j < N - 3; j++) {   //按顺序选取点
			Point p = pArrayOrigin[j];
			Arrays.sort(pArray, p.SLOPE_ORDER);  //排序

			int k = 0;

			while (k < N - 2) {
				int l = k;
				while (++l < N
						&& (p.slopeTo(pArray[k])) == (p.slopeTo(pArray[l])))  //找到相同的斜率的点的个数
					;
				if (l - k >= 3) {   //如果个数大于3
					Point[] tmpPoints = new Point[l - k];
					for (int i = k, x = 0; i < l; i++, x++)
						tmpPoints[x] = pArray[i];   //将相同斜率的点复制进临时数组
					Arrays.sort(tmpPoints);         //排序
					if (p.compareTo(tmpPoints[0]) > 0) {  //如果基点比后面的第一个点大，说明基点不是开始的
						k = l;                            //此种情况舍去
						continue;
					}
					p.drawTo(tmpPoints[l - k - 1]);
					StdOut.print(p.toString());
					for (int i = 0; i < l - k; i++)
						StdOut.print(" -> " + tmpPoints[i].toString());

					StdOut.print("\n");
				}
				k = l;
			}
		}
		StdDraw.show(0);
		long cur = System.currentTimeMillis();
		StdOut.println(cur - pre);   //测时
	}
}
