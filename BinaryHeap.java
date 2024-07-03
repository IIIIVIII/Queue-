import java.lang.reflect.Array;

public abstract class BinaryHeap<T extends Comparable<T>>
{
	protected int capacity;

	protected int initCapacity;

	protected T[] items;

	protected int numItems;

	protected Class<T> baseclass;

	public enum CONSTRUCT { BOTTOM_UP, TOP_DOWN }

	//
	//
	@SuppressWarnings("unchecked")
	public BinaryHeap(Class<T> c)
	{
		capacity = 10;
		initCapacity = 10;
		numItems = 0;
		baseclass = c;
		items = (T[]) Array.newInstance(c, capacity);
	}


	//
	@SuppressWarnings("unchecked")
	public BinaryHeap(Class<T> c, T[] A)
	{
		assert(A != null);

		capacity = A.length;
		initCapacity = A.length;
		numItems = A.length;
		baseclass = c;
		items = (T[]) Array.newInstance(c, A.length);

		for (int i = 0; i < A.length; i += 1)
		{
			items[i] = A[i];
		}
	}

	//
	// Inserts an item into the binary heap.
	//
	public abstract void insert(T item);


	public boolean isEmpty()
	{
		return numItems == 0;
	}


	protected int lastParent()
	{
		return (numItems / 2) - 1;
	}


	protected int leftChild(int i)
	{
		int left = 2 * i + 1;
		return (left < numItems) ? left : -1;
	}


	protected int maxChild(int i)
	{
		int left = leftChild(i);
		int right = rightChild(i);
		if (left == -1) return -1;
		if (right == -1) return left;
		return (items[left].compareTo(items[right]) > 0) ? left : right;
	}

	//
	// Returns the index of the min child of index i. If i has no children then
	// returns -1.
	//
	protected int minChild(int i)
	{
		int left = leftChild(i);
		int right = rightChild(i);
		if (left == -1) return -1;
		if (right == -1) return left;
		return (items[left].compareTo(items[right]) < 0) ? left : right;
	}

	//
	//
	public abstract T next() throws EmptyHeapException;

	//
	//
	protected int parent(int i)
	{
		return (i == 0) ? -1 : (i - 1) / 2;
	}

	//
	//
	public T peek() throws EmptyHeapException
	{
		if (numItems == 0)
		{
			throw new EmptyHeapException();
		}

		return items[0];
	}

	//
	//
	public void print()
	{
		StringBuilder SB = new StringBuilder();

		SB.append('[');

		if (numItems > 0)
		{
			SB.append(items[0]);

			for (int i = 1; i < numItems; i += 1)
			{
				SB.append(", ");
				SB.append(items[i]);
			}
		}

		SB.append(']');

		System.out.print(SB.toString());
	}

	//
	//
	protected void resize(int m)
	{
		assert(m > 0);

		if (m < initCapacity)
		{
			m = initCapacity;
		}

		@SuppressWarnings("unchecked")
		T[] newItems = (T[]) Array.newInstance(baseclass, m);

		for (int i = 0; i < numItems; i += 1)
		{
			newItems[i] = items[i];
		}

		items = newItems;
		capacity = m;
	}

	//
	//
	protected int rightChild(int i)
	{
		int right = 2 * i + 2;
		return (right < numItems) ? right : -1;
	}

	//
	//
	public int size()
	{
		return numItems;
	}

	//
	//
	protected void swap(int i, int j)
	{
		assert(i >= 0 && i < numItems);
		assert(j >= 0 && j < numItems);
		T t = items[i];
		items[i] = items[j];
		items[j] = t;
	}

}
