import java.util.Arrays;

public class Board
{
	private char[] board;
	private int dim;
	private int sz;
	private int man = -1;
	private int ham;

	// private static long[] fac=new long[20];
	//
	// private static long getPermutationNumForInt(int[] perInt,int len)
	// {
	// int num=0;
	// boolean[] used=new boolean[21];
	//
	// for(int i=0;i<len;i++)
	// {
	// int n=0;
	//
	// for(int j=0;j<perInt[i];++j)
	// if(!used[j])
	// ++n;
	//
	// num+=n*fac[len-i-1];
	// used[perInt[i]]=true;
	// }
	//
	// return num;
	// }
	//
	// private static long getPemutationNum(char[] s1,char[] s2,int len)
	// {
	// int[] perInt=new int[len];
	//
	// for(int i=0;i<len;++i)
	// for(int j=0;j<len;++j)
	// if(s2[i]==s1[j])
	// {
	// perInt[i]=j;
	// break;
	// }
	// long num=getPermutationNumForInt(perInt, len);
	// return num;
	// }
	//
	// private static void genPermutationByNum(char[] s1,char[] s2,int len,long
	// No)
	// {
	// int[] perInt=new int[21];
	// boolean[] used=new boolean[21];
	//
	// for(int i=0;i<len;++i)
	// {
	// int j;
	//
	// for(j=0;j<len;++j)
	// if(!used[j])
	// {
	// if(fac[len-i-1]>=No+1)
	// break;
	// else No-=fac[len-i-1];
	// }
	// perInt[i]=j;
	// used[j]=true;
	// }
	// for(int i=0;i<len;i++)
	// s2[i]=s1[perInt[i]];
	// }
	//
	// private long StrStatusToInt(char[] str)
	// {
	// return getPemutationNum(board, str, sz);
	// }
	//
	// private void IntStatusToStr(int n,char[] str)
	// {
	// genPermutationByNum(board, str, sz, n);
	// }

	public Board(int[][] blocks) // construct a board from an N-by-N array of
									// blocks
									// (where blocks[i][j] = block in row i,
									// column j)
	{
		dim = blocks.length;
		sz = dim * dim;
		board = new char[sz];
		// goalStr=new char[sz];

		for (int i = 0; i < dim; i++)
		{
			for (int j = 0; j < dim; j++)
			{
				board[i * dim + j] = (char) (blocks[i][j] + '0');
			}
		}
		// for(int i=0;i<sz-1;i++)
		// goalStr[i]=(char)(i+'1');
		// goalStr[sz-1]='0';

		// goal=StrStatusToInt(goalStr);
		// nStatus=StrStatusToInt(board);
	}

	public int dimension() // board dimension N
	{
		return dim;
	}

	public int hamming() // number of blocks out of place
	{
		int num = 0;

		for (int i = 0; i < sz; i++)
		{
			if (board[i] == '0')
				continue;
			else if (board[i] != i + 48 + 1)
				num++;
		}
		ham = num;
		return num;
	}

	public int manhattan() // sum of Manhattan distances between blocks and goal
	{
		if (man == -1)
		{
			int num = 0;

			for (int i = 0; i < sz; i++)
			{
				if (board[i] == '0')
					continue;
				int deltaCol = (int) (board[i] - 1 - 48) / dim - i / dim;
				int deltaRow = (int) (board[i] - 1 - 48) % dim - i % dim;
				num += Math.abs(deltaCol) + Math.abs(deltaRow);
			}
			man = num;
		}
		return man;
	}

	public boolean isGoal() // is this board the goal board?
	{
		return manhattan() == 0;
	}

	public Board twin() // a board obtained by exchanging two adjacent blocks in
						// the same row
	{
		int[][] tmp = new int[dim][dim];
		int count = 0;
		int x = 0;

		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				tmp[i][j] = board[count++] - '0';

		if (board[0] != '0' && board[1] != '0')
			x = 0;
		else
			x = 1;

		int temp = tmp[x][0];
		tmp[x][0] = tmp[x][1];
		tmp[x][1] = temp;

		return new Board(tmp);
	}

	public boolean equals(Object y) // does this board equal y?
	{
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		if (((Board) y).dim != dim)
			return false;

		return Arrays.equals(((Board) y).board, this.board);
	}

	public Iterable<Board> neighbors() // all neighboring boards
	{
		int[][] tmp = new int[dim][dim];
		int count = 0;

		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				tmp[i][j] = board[count++] - '0';

		Queue<Board> tmpQueue = new Queue<Board>();
		int x = 0;

		for (; x < sz; x++)
			if (board[x] == '0')
				break;
		int i0 = x / dim;
		int j0 = x % dim;

		for (int i1 = -1; i1 <= 1; i1++)
			for (int j1 = -1; j1 <= 1; j1++)
			{
				if (i0 + i1 < 0 || i0 + i1 >= dim || j0 + j1 < 0 || j0 + j1 >= dim || i1 == j1 || i1 == -j1)
					continue;
				int temp = tmp[i0 + i1][j0 + j1];
				tmp[i0 + i1][j0 + j1] = tmp[i0][j0];
				tmp[i0][j0] = temp;
				Board aBoard = new Board(tmp);

				int deltaCol1 = (int) (tmp[i0][j0] - 1) / dim - i0 - i1;
				int deltaRow1 = (int) (tmp[i0][j0] - 1) % dim - j0 - j1;
				int deltaCol2 = (int) (tmp[i0][j0] - 1) / dim - i0;
				int deltaRow2 = (int) (tmp[i0][j0] - 1) % dim - j0;

				aBoard.man = manhattan() + (Math.abs(deltaCol2) + Math.abs(deltaRow2) - Math.abs(deltaCol1) - Math.abs(deltaRow1));
				tmpQueue.enqueue(aBoard);

				temp = tmp[i0 + i1][j0 + j1];
				tmp[i0 + i1][j0 + j1] = tmp[i0][j0];
				tmp[i0][j0] = temp;
			}

		return tmpQueue;
	}

	public String toString() // string representation of the board (in the
								// output format specified below)
	{
		StringBuilder s = new StringBuilder();
		s.append(dim + "\n");
		for (int i = 0; i < dim; i++)
		{
			for (int j = 0; j < dim; j++)
			{
				s.append(String.format("%2d ", (char) (board[i * dim + j]) - 48));
			}
			s.append("\n");
		}
		return s.toString();
	}
}
