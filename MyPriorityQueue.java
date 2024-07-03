public class MyPriorityQueue<T extends Comparable<T>> {
    private MaxHeap<T> heap;

    public MyPriorityQueue(Class<T> c) {
        heap = new MaxHeap<>(c);
    }

    public void enqueue(T item) {
        heap.insert(item);
    }

    public T dequeue() throws EmptyHeapException {
        return heap.next();
    }

    // Peek at the item with the highest priority without removing it.
    public T peek() throws EmptyHeapException {
        return heap.peek();
    }

    // Check if the priority queue is empty.
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Get the size of the priority queue.
    public int size() {
        return heap.size();
    }
}
