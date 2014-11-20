import java.util.Comparator;
import java.util.Iterator;

public class Solver
{
	private boolean flag = true;
	private int move = 0;
	private Stack<Board> result;
	private Comparator<Node> cmp = new myCmp();

	private class Node
	{
		private Node preBoard;
		private Board curBoard;
		private int times;

		public Node(Node pre, Board cur, int curTimes)
		{
			preBoard = pre;
			curBoard = cur;
			times = curTimes;
		}

		public Board preBoard()
		{
			if (preBoard == null)
				return null;
			return preBoard.curBoard();
		}

		public Board curBoard()
		{
			return curBoard;
		}

		public int times()
		{
			return times;
		}
	}

	private class myCmp implements Comparator<Node>
	{

		public int compare(Node arg0, Node arg1)
		{
			if (arg0.curBoard.manhattan() + arg0.times > arg1.curBoard().manhattan() + arg1.times)
				return 1;
			if (arg0.curBoard.manhattan() + arg0.times < arg1.curBoard().manhattan() + arg1.times)
				return -1;
			if (arg0.curBoard.manhattan() + arg0.times == arg1.curBoard().manhattan() + arg1.times)
			{
				if (arg0.curBoard.manhattan() > arg1.curBoard.manhattan())
					return 1;
				if (arg0.curBoard.manhattan() < arg1.curBoard.manhattan())
					return -1;
			}
			return 0;
		}

	}

	public Solver(Board initial) // find a solution to the initial board (using
									// the A* algorithm)
	{
		MinPQ<Node> pq1 = new MinPQ<Node>(100000, cmp);
		MinPQ<Node> pq2 = new MinPQ<Node>(100000, cmp);

		Node status = new Node(null, initial, 0);
		Node status2 = new Node(null, initial.twin(), 0);

		Stack<Node> nodeQueue = new Stack<Node>();

		pq1.insert(status);
		pq2.insert(status2);

		while (!status.curBoard.isGoal())
		{
			if (status2.curBoard.isGoal())
			{
				flag = false;
				move = -1;
				break;
			}
			nodeQueue.push(status);

			boolean equalFlag = false;
			boolean equalFlag2 = false;

			if (!pq1.isEmpty())
			{
				status = pq1.delMin();
				Iterator<Board> i1 = status.curBoard.neighbors().iterator();
				while (i1.hasNext())
				{
					Board neighborbBoard = i1.next();
					if (!equalFlag && neighborbBoard.equals(status.preBoard()))
					{
						equalFlag = true;
						continue;
					}
					Node neighbor = new Node(status, neighborbBoard, status.times + 1);
					pq1.insert(neighbor);
				}
			} else
			{
				flag = false;
				move = -1;
				break;
			}

			if (!pq2.isEmpty())
			{
				status2 = pq2.delMin();
				Iterator<Board> i2 = status2.curBoard.neighbors().iterator();
				while (i2.hasNext())
				{
					Board neighborbBoard = i2.next();
					if (!equalFlag2 && neighborbBoard.equals(status2.preBoard()))
					{
						equalFlag2 = true;
						continue;
					}
					Node neighbor = new Node(status2, neighborbBoard, status2.times + 1);
					pq2.insert(neighbor);
				}
			}
		}
		if (flag)
		{
			result = new Stack<Board>();
			nodeQueue.push(status);
			Node resnode = nodeQueue.pop();
			move = resnode.times();
			while (resnode != null)
			{
				result.push(resnode.curBoard);
				resnode = resnode.preBoard;
			}
		}
	}

	public boolean isSolvable() // is the initial board solvable?
	{
		return flag;
	}

	public int moves() // min number of moves to solve initial board; -1 if no
						// solution
	{
		return move;
	}

	public Iterable<Board> solution() // sequence of boards in a shortest
										// solution; null if no solution
	{
		return result;
	}

	public static void main(String[] args) // solve a slider puzzle (given
											// below)
	{
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else
		{
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
