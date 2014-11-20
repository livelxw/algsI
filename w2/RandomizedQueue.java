import java.util.Iterator;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] array;
	private int sz;

	private void expand() {
		Item[] old = array;
		array = (Item[]) new Object[array.length * 2];
		for (int i = 0; i < sz; i++)
			array[i] = old[i];
	}

	private void shrunk() {
		if (array.length == 1)
			return;
		Item[] old = array;
		array = (Item[]) new Object[array.length / 2];
		for (int i = 0; i < sz; i++)
			array[i] = old[i];
	}

	public RandomizedQueue() {
		array = (Item[]) new Object[1];
		sz = 0;
	}

	public boolean isEmpty() {
		if (sz == 0)
			return true;
		return false;
	}

	public int size() {
		return sz;
	}

	public void enqueue(Item item) throws NullPointerException {
		if (item == null)
			throw new NullPointerException("NullPointerException");
		if (sz == array.length)
			expand();
		array[sz++] = item;
	}

	public Item dequeue() throws java.util.NoSuchElementException {
		if (0 == sz)
			throw new java.util.NoSuchElementException("NoSuchElementException");
		int index = StdRandom.uniform(0, sz);
		Item tmp = array[index];
		array[index] = array[--sz];
		array[sz] = null;
		if (sz == array.length / 2)
			shrunk();
		return tmp;
	}

	public Item sample() throws java.util.NoSuchElementException {
		if (0 == sz)
			throw new java.util.NoSuchElementException("NoSuchElementException");
		return array[StdRandom.uniform(0, sz)];
	}

	public Iterator<Item> iterator() {
		return new RandIter();
	}

	private class RandIter implements Iterator<Item> {
		private int cur = 0;
		private Item[] iterArray;

		RandIter() {
			iterArray = (Item[]) new Object[sz];
			for (int i = 0; i < sz; i++)
				iterArray[i] = array[i];
			StdRandom.shuffle(iterArray);
		}

		public boolean hasNext() {
			return cur != sz;
		}

		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException(
					"UnsupportedOperationException");
		}

		public Item next() throws java.util.NoSuchElementException {
			if (cur == sz)
				throw new java.util.NoSuchElementException(
						"NoSuchElementException");
			return iterArray[cur++];
		}
	}
}
