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

		for (int j = 0; j < N - 3; j++) {   //��˳��ѡȡ��
			Point p = pArrayOrigin[j];
			Arrays.sort(pArray, p.SLOPE_ORDER);  //����

			int k = 0;

			while (k < N - 2) {
				int l = k;
				while (++l < N
						&& (p.slopeTo(pArray[k])) == (p.slopeTo(pArray[l])))  //�ҵ���ͬ��б�ʵĵ�ĸ���
					;
				if (l - k >= 3) {   //�����������3
					Point[] tmpPoints = new Point[l - k];
					for (int i = k, x = 0; i < l; i++, x++)
						tmpPoints[x] = pArray[i];   //����ͬб�ʵĵ㸴�ƽ���ʱ����
					Arrays.sort(tmpPoints);         //����
					if (p.compareTo(tmpPoints[0]) > 0) {  //�������Ⱥ���ĵ�һ�����˵�����㲻�ǿ�ʼ��
						k = l;                            //���������ȥ
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
		StdOut.println(cur - pre);   //��ʱ
	}
}
