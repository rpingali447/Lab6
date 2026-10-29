import java.util.NoSuchElementException;

/**
 * A min-heap of ints stored in an array, as presented in lecture.
 *
 * The heap is a complete binary tree kept in heap[1] .. heap[size];
 * heap[0] is never used. For the node at index i:
 *     left child  = 2 * i
 *     right child = 2 * i + 1
 *     parent      = i / 2 
 *
 * Heap-order property: every parent is <= its children, so the
 * minimum is always at heap[1]. Duplicate values are allowed.
 *
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 */
public class MinHeap {

    /** Number of values a new heap can hold before it has to grow. */
    public static final int DEFAULT_CAPACITY = 10;

    private int[] heap;   // heap[0] is unused; the root is heap[1]
    private int size;     // values live in heap[1] ... heap[size]

    /** Creates an empty heap with room for DEFAULT_CAPACITY values. */
    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty heap with room for capacity values before it grows.
     * (The array needs capacity + 1 slots, since index 0 is unused.)
     * throws IllegalArgumentException if capacity is less than 1
     * Example: new MinHeap(3) holds 3 values before it grows;
     * new MinHeap(0) throws IllegalArgumentException.
     */
    public MinHeap(int capacity) {
        // TODO: validate capacity, then create the array
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be at least 1");
        }
        heap = new int[capacity + 1];
        size = 0;
    }

    /**
     * Builds a heap from values bottom-up in O(n): copies them into
     * heap[1] .. heap[n] in the given order, then calls buildHeap().
     * values itself must not be changed. (Make the array exactly big
     * enough: n + 1 slots, but at least 2.)
     * throws IllegalArgumentException if values is null
     * Example: new MinHeap(new int[]{5, 4, 3, 2, 1}).toArray() returns
     * {1, 2, 3, 5, 4}.
     */
    public MinHeap(int[] values) {
        // TODO
        if (values == null) {
            throw new IllegalArgumentException("values must not be null");
        }
        heap = new int[Math.max(values.length + 1, 2)];
        for (int i = 0; i < values.length; i++) {
            heap[i + 1] = values[i];
        }
        size = values.length;
        buildHeap();
    }

    /**
     * Adds value to the heap: puts it in the next open spot (index size + 1),
     * then percolates it up until its parent is no larger.
     * Grows the array first if it is full.
     * Example: inserting 4 into {3, 5, 8, 10, 7, 12, 9} gives
     * {3, 4, 8, 5, 7, 12, 9, 10}.
     */
    public void insert(int value) {
        // TODO
        if (size == heap.length - 1) {
            grow();
        }
        size++;
        heap[size] = value;
        percolateUp(size);
    }

    /**
     * Removes and returns the smallest value: the last leaf moves to the
     * root, then percolates down until it is no larger than its children.
     * throws NoSuchElementException if the heap is empty
     * Example: on {3, 5, 8, 10, 7, 12, 9} returns 3, leaving
     * {5, 7, 8, 10, 9, 12}.
     */
    public int removeMin() {
        // TODO
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        int min = heap[1];
        heap[1] = heap[size];
        size--;
        percolateDown(1);
        return min;
    }

    /**
     * Returns the smallest value without removing it.
     * throws NoSuchElementException if the heap is empty
     * Example: on {3, 5, 8, 10, 7, 12, 9} returns 3 and the heap is unchanged.
     */
    public int peek() {
        // TODO
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        return heap[1];
    }

    /**
     * Returns the number of values in the heap.
     * Example: after inserting 3, 5, 8 this returns 3.
     */
    public int size() {
        // TODO
        return size;
    }

    /**
     * Reports whether the heap has no values.
     * return true if the heap is empty
     * Example: new MinHeap().isEmpty() returns true; after insert(1) it returns false.
     */
    public boolean isEmpty() {
        // TODO
        return size == 0;
    }

    /**
     * Returns the heap's values in array order (heap[1] ... heap[size]) as a
     * new array of length size(). Changing the returned array must not
     * change the heap.
     * Example: after inserting 3, 5, 8, 10, 7, 12, 9 this returns
     * {3, 5, 8, 10, 7, 12, 9}.
     * (The insert order matches the heap array order in this example)
     */
    public int[] toArray() {
        // TODO
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = heap[i + 1];
        }
        return result;
    }

    /**
     * Moves the value at index i up while it is smaller than its parent.
     * (Stop when it reaches the root or its parent is <= it.)
     * Example: in {3, 5, 8, 10, 7, 12, 9, 4}, percolateUp(8) gives
     * {3, 4, 8, 5, 7, 12, 9, 10}.
     */
    private void percolateUp(int i) {
        // TODO
        while (i > 1 && heap[i] < heap[i / 2]) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    /**
     * Moves the value at index i down, always swapping with the smaller
     * child (the LEFT child if both children are equal), until it is no
     * larger than its children or reaches a leaf.
     * Example: in {9, 5, 8, 10, 7, 12}, percolateDown(1) gives
     * {5, 7, 8, 10, 9, 12}.
     */
    private void percolateDown(int i) {
        // TODO
        while (2 * i <= size) {
            int child = 2 * i;
            if (child + 1 <= size && heap[child + 1] < heap[child]) {
                child++;
            }
            if (heap[i] <= heap[child]) {
                break;
            }
            swap(i, child);
            i = child;
        }
    }

    /**
     * Exchanges heap[a] and heap[b].
     * a and b are the indices of the values to swap
     * Example: on {3, 5, 8}, swap(2, 3) gives {3, 8, 5}
     */
    private void swap(int a, int b) {
        // TODO
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    /** Doubles the array's capacity, keeping every value at the same index.
     * Example: a full heap with capacity 10 (array length 11) ends up with
     * capacity 20 (array length 21).
     */
    private void grow() {
        // TODO
        int capacity = heap.length - 1;
        int[] bigger = new int[2 * capacity + 1];
        for (int i = 1; i <= size; i++) {
            bigger[i] = heap[i];
        }
        heap = bigger;
    }

    /**
     * Bottom up heap construction.
     * Restores heap order over heap[1] .. heap[size] by percolating down
     * each non-leaf node, starting at the last one (index size / 2) and
     * working back to the root (index 1).
     * Example: {5, 4, 3, 2, 1} becomes {1, 2, 3, 5, 4}.
     */
    private void buildHeap() {
        // TODO
        for (int i = size / 2; i >= 1; i--) {
            percolateDown(i);
        }
    }

    /**
     * Returns a new array holding values in ascending order: builds a
     * MinHeap from values with the MinHeap(int[]) constructor, then calls
     * removeMin() repeatedly. values itself must not be changed.
     * throws IllegalArgumentException if values is null
     * Example: heapSort(new int[]{3, 1, 2}) returns {1, 2, 3}.
     */
    public static int[] heapSort(int[] values) {
        // TODO
        MinHeap h = new MinHeap(values);
        int[] result = new int[values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = h.removeMin();
        }
        return result;
    }
}
