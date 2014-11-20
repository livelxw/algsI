import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private class node {
		Item item;
		node next;
		node pri;
	}

	private node first;
	private node last;
	private int sz;

	public Deque() {
		first = new node();
		last = new node();
		first.next = last;
		last.pri = first;
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

	public void addFirst(Item item) throws NullPointerException {
		if (item == null)
			throw new NullPointerException("NullPointerException");
		node add = new node();
		node old = first.next;

		add.item = item;
		add.pri = first;
		add.next = old;
		first.next = add;
		old.pri = add;
		sz++;
	}

	public void addLast(Item item) throws NullPointerException {
		if (item == null)
			throw new NullPointerException("NullPointerException");
		node add = new node();
		node old = last.pri;

		add.item = item;
		add.pri = old;
		add.next = last;
		last.pri = add;
		old.next = add;
		sz++;
	}

	public Item removeFirst() throws java.util.NoSuchElementException {
		if (isEmpty())
			throw new java.util.NoSuchElementException("NoSuchElementException");
		node del = first.next;
		first.next = del.next;
		del.next.pri = first;
		sz--;
		return del.item;
	}

	public Item removeLast() throws java.util.NoSuchElementException {
		if (isEmpty())
			throw new java.util.NoSuchElementException("NoSuchElementException");
		node del = last.pri;
		last.pri = del.pri;
		del.pri.next = last;
		sz--;
		return del.item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private node cur = first.next;

		public boolean hasNext() {
			return cur != last;
		}

		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException(
					"UnsupportedOperationException");
		}

		public Item next() throws java.util.NoSuchElementException {
			if (cur == last)
				throw new java.util.NoSuchElementException(
						"NoSuchElementException");
			Item item = cur.item;
			cur = cur.next;
			return item;
		}
	}
}
