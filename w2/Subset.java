public class Subset {
	private RandomizedQueue<String> rq;

	public static void main(String[] args) {
		Subset ss = new Subset();
		int k = Integer.parseInt(args[0]);
		ss.rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			ss.rq.enqueue(StdIn.readString());
		}
		for (int i = 0; i < k; i++)
			StdOut.println(ss.rq.dequeue());
	}

}
