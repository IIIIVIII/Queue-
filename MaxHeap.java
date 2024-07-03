import java.lang.reflect.Array;

public class MaxHeap<T extends Comparable<T>> extends BinaryHeap<T>
{
	public MaxHeap(Class<T> c)
	{
		super(c);
	}

	public MaxHeap(Class<T> c, T[] A, CONSTRUCT type)
	{
		super(c, A);

		switch (type)
		{
			case BOTTOM_UP:
			{
				bottomUp();
				break;
			}

			case TOP_DOWN:
			{
				topDown();
				break;
			}

			default:
			{
				bottomUp();
			}
		}
	}

	private void bottomUp()
	{
		for (int i = lastParent(); i >= 0; i--) {
			sinkDown(i);
		}
	}

	public void insert(T item)
	{
		if (numItems == capacity) {
			resize(2 * capacity);
		}
		items[numItems] = item;
		swimUp(numItems);
		numItems++;
	}

	private boolean isMaxHeapOrder(int i)
	{
		int left = leftChild(i);
		int right = rightChild(i);

		if (left != -1 && items[i].compareTo(items[left]) < 0) {
			return false;
		}
		if (right != -1 && items[i].compareTo(items[right]) < 0) {
			return false;
		}
		return true;
	}

	public T next() throws EmptyHeapException
	{
		if (numItems == 0) {
			throw new EmptyHeapException();
		}
		T max = items[0];
		items[0] = items[--numItems];
		items[numItems] = null; // Avoid loitering
		sinkDown(0);
		return max;
	}

	private void sinkDown(int i)
	{
		int left, right, max;

		while (!isMaxHeapOrder(i)) {
			left = leftChild(i);
			right = rightChild(i);

			if (right == -1) {
				max = left;
			} else {
				max = items[left].compareTo(items[right]) > 0 ? left : right;
			}

			swap(i, max);
			i = max;
		}
	}

	private void swimUp(int i)
	{
		int parent = parent(i);
		while (i > 0 && items[i].compareTo(items[parent]) > 0) {
			swap(i, parent);
			i = parent;
			parent = parent(i);
		}
	}

	private void topDown()
	{
		T[] temp = (T[]) Array.newInstance(baseclass, capacity);
		int count = 0;

		for (T item : items) {
			if (item != null) {
				temp[count] = item;
				swimUp(count);
				count++;
			}
		}

		items = temp;
		numItems = count;
	}

	public T peek() throws EmptyHeapException {
		if (numItems == 0) {
			throw new EmptyHeapException();
		}
		return items[0];
	}

	public int size() {
		return numItems;
	}
}
