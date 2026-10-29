import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MinHeap.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class MinHeapTest {

    // ---- starter tests ----

    @Test
    void newHeapIsEmpty() {
        MinHeap h = new MinHeap();
        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
    }

    @Test
    void peekOnEmptyHeapThrows() {
        assertThrows(NoSuchElementException.class, () -> new MinHeap().peek());
    }

    @Test
    void insertOneValue() {
        MinHeap h = new MinHeap(1);
        h.insert(42);
        assertFalse(h.isEmpty());
        assertEquals(1, h.size());
        assertEquals(42, h.peek());
        assertArrayEquals(new int[]{42}, h.toArray());
    }

    // TODO: add your own tests. At a minimum, cover:
    //   - removeMin() on an empty heap throws NoSuchElementException
    //   - new MinHeap(0) throws IllegalArgumentException
    //   - peek() does not remove the value
    //   - inserting a new minimum percolates all the way to the root
    //   - removeMin() when both children are equal swaps with the LEFT child
    //   - duplicate and negative values
    //   - inserting more values than the initial capacity (growing)
    //   - toArray() returns a copy (changing it does not change the heap)
    //   - many inserts followed by removeMin() return values in sorted order
    //   - new MinHeap(int[]): an example that comes out DIFFERENT from inserting
    //     the same values one at a time, an empty array, one value, and that the
    //     caller's array is not changed
    //   - a heap built from an array still supports insert (it must grow) and removeMin
    //   - heapSort: empty array, duplicates, negatives, input not changed


    private static MinHeap exampleHeap() {
        MinHeap h = new MinHeap();
        for (int v : new int[]{3, 5, 8, 10, 7, 12, 9}) {
            h.insert(v);
        }
        return h;
    }

    @Test
    void removeMinOnEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> new MinHeap().removeMin());
    }

    @Test
    void zeroCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new MinHeap(0));
    }

    @Test
    void peekDoesNotRemove() {
        MinHeap h = exampleHeap();
        assertEquals(3, h.peek());
        assertEquals(3, h.peek());
        assertEquals(7, h.size());
        assertArrayEquals(new int[]{3, 5, 8, 10, 7, 12, 9}, h.toArray());
    }

    @Test
    void newMinimumGoesToRoot() {
        MinHeap h = exampleHeap();
        h.insert(1);
        assertEquals(1, h.peek());
        assertArrayEquals(new int[]{1, 3, 8, 5, 7, 12, 9, 10}, h.toArray());
    }

    @Test
    void removeMinTieSwapsLeft() {
        MinHeap h = new MinHeap(new int[]{1, 2, 2, 5, 6, 3, 4});
        assertArrayEquals(new int[]{1, 2, 2, 5, 6, 3, 4}, h.toArray());
        assertEquals(1, h.removeMin());
        assertArrayEquals(new int[]{2, 4, 2, 5, 6, 3}, h.toArray());
    }

    @Test
    void duplicateValues() {
        MinHeap h = new MinHeap();
        for (int v : new int[]{4, 2, 4, 2, 4, 2}) {
            h.insert(v);
        }
        int[] out = new int[6];
        for (int i = 0; i < 6; i++) {
            out[i] = h.removeMin();
        }
        assertArrayEquals(new int[]{2, 2, 2, 4, 4, 4}, out);
    }

    @Test
    void negativeValues() {
        MinHeap h = new MinHeap();
        for (int v : new int[]{3, -1, -7, 0, 5, -2}) {
            h.insert(v);
        }
        assertEquals(-7, h.peek());
        int[] out = new int[6];
        for (int i = 0; i < 6; i++) {
            out[i] = h.removeMin();
        }
        assertArrayEquals(new int[]{-7, -2, -1, 0, 3, 5}, out);
    }

    @Test
    void insertPastCapacityGrows() {
        MinHeap h = new MinHeap(2);
        for (int v = 10; v >= 1; v--) {
            h.insert(v);
        }
        assertEquals(10, h.size());
        assertEquals(1, h.peek());
        for (int v = 1; v <= 10; v++) {
            assertEquals(v, h.removeMin());
        }
    }

    @Test
    void toArrayReturnsCopy() {
        MinHeap h = exampleHeap();
        int[] a = h.toArray();
        a[0] = 999;
        a[1] = -999;
        assertEquals(3, h.peek());
        assertArrayEquals(new int[]{3, 5, 8, 10, 7, 12, 9}, h.toArray());
    }

    @Test
    void manyInsertsSorted() {
        MinHeap h = new MinHeap();
        for (int i = 50; i >= 1; i--) {
            h.insert(i);
        }
        for (int i = 1; i <= 50; i++) {
            assertEquals(i, h.removeMin());
        }
        assertTrue(h.isEmpty());
    }

    @Test
    void buildDiffersFromInserting() {
        int[] values = {5, 4, 3, 2, 1};
        MinHeap built = new MinHeap(values);
        MinHeap inserted = new MinHeap();
        for (int v : values) {
            inserted.insert(v);
        }
        assertArrayEquals(new int[]{1, 2, 3, 5, 4}, built.toArray());
        assertArrayEquals(new int[]{1, 2, 4, 5, 3}, inserted.toArray());
    }

    @Test
    void buildFromEmptyArray() {
        MinHeap h = new MinHeap(new int[0]);
        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
        assertThrows(NoSuchElementException.class, h::peek);
    }

    @Test
    void buildFromOneValue() {
        MinHeap h = new MinHeap(new int[]{9});
        assertEquals(1, h.size());
        assertEquals(9, h.peek());
        assertArrayEquals(new int[]{9}, h.toArray());
    }

    @Test
    void buildKeepsCallersArray() {
        int[] values = {5, 4, 3, 2, 1};
        new MinHeap(values);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, values);
    }

    @Test
    void builtHeapInsertGrows() {
        MinHeap h = new MinHeap(new int[]{5, 4, 3, 2, 1});
        h.insert(0);
        h.insert(6);
        assertEquals(7, h.size());
        assertEquals(0, h.peek());
        assertArrayEquals(new int[]{0, 2, 1, 5, 4, 3, 6}, h.toArray());
    }

    @Test
    void builtHeapRemoveMin() {
        MinHeap h = new MinHeap(new int[]{5, 4, 3, 2, 1});
        int[] out = new int[5];
        for (int i = 0; i < 5; i++) {
            out[i] = h.removeMin();
        }
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, out);
        assertTrue(h.isEmpty());
    }

    @Test
    void heapSortEmpty() {
        assertArrayEquals(new int[0], MinHeap.heapSort(new int[0]));
    }

    @Test
    void heapSortDuplicates() {
        assertArrayEquals(new int[]{1, 1, 2, 2, 2, 3},
                MinHeap.heapSort(new int[]{2, 1, 3, 2, 1, 2}));
    }

    @Test
    void heapSortNegatives() {
        assertArrayEquals(new int[]{-9, -3, 0, 4, 8},
                MinHeap.heapSort(new int[]{4, -3, 8, -9, 0}));
    }

    @Test
    void heapSortKeepsInput() {
        int[] values = {4, 1, 5, 3, 2};
        int[] result = MinHeap.heapSort(values);
        assertArrayEquals(new int[]{4, 1, 5, 3, 2}, values);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }


    @Test
    void removeMinRightChild() {
        MinHeap h = new MinHeap(new int[]{1, 6, 2, 7, 8, 3, 4});
        assertEquals(1, h.removeMin());
        assertArrayEquals(new int[]{2, 6, 3, 7, 8, 4}, h.toArray());
    }

    @Test
    void removeMinStopsEarly() {
        MinHeap h = new MinHeap();
        h.insert(1);
        h.insert(2);
        h.insert(3);
        assertEquals(1, h.removeMin());
        assertArrayEquals(new int[]{2, 3}, h.toArray());
    }

    @Test
    void negativeCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new MinHeap(-5));
    }

    @Test
    void buildFromNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> new MinHeap((int[]) null));
    }

    @Test
    void heapSortNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> MinHeap.heapSort(null));
    }
}
