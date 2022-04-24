/**  first part of project1A.
 *   Deque implemented by Array
 *  @author PengWang
 */
public class ArrayDeque<T> {
    private T[] array;
    private int size;
    private int length;
    private int front;
    private int back;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        length = 8;
        front = 4;
        back = 4;
    }
    // front and back handles new elements in different ways, so that before growing we
    // get front < back as invariant. Check addFirst and addLast. front will be
    // decremented first, then assigned a value; while back will be assigned a value
    // first, then increased; both should be considered with modulo.
    // Double the size and copy all the elements into new array;
    private int posMinusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }

    private int posPlusOne(int index, int len) {
        if (index == len - 1) {
            return 0;
        }
        return index + 1;
    }

    private void grow() {
        T[] copyArray = (T[]) new Object[length * 2];
        int ptr1 = front;
        int ptr2 = length;
        while (ptr1 != back) {
            copyArray[ptr2] = array[ptr1];
            ptr1 = posPlusOne(ptr1, length);
            ptr2 = posPlusOne(ptr2, length * 2);
        }
        front = length;
        back = ptr2;
        array = copyArray;
        length *= 2;
    }

    // Shrink the size to 1/4 whenever actual elements decrease beyond that;
    private void shrink() {
        T[] copyArray = (T[]) new Object [length / 2];
        int ptr1 = front;
        int ptr2 = length / 4;
        while (ptr1 != back) {
            copyArray[ptr2] = array[ptr1];
            ptr1 = posPlusOne(ptr1, length);
            ptr2 = posPlusOne(ptr2, length / 2);
        }
        front = length / 4;
        back = ptr2;
        array = copyArray;
        length /= 2;
    }

    public void addFirst(T item) {
        if (size == length - 1) {
            grow();
        }
        front = posMinusOne(front);
        array[front] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == length - 1) {
            grow();
        }
        array[back] = item;
        back = posPlusOne(back, length);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int ptr = front;
        while (ptr != back) {
            System.out.print(array[ptr] + " ");
            ptr = posPlusOne(ptr, length);
        }
    }

    public T removeFirst() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        T ans = array[front]; //remember, front is already in use whenever initialized;
        front = posPlusOne(front, length);
        size--;
        return ans;
    }

    public T removeLast() {
        if (length > 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        back = posMinusOne(back);
        size--;
        return array[back];
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = front;
        for (int i = 0; i < index; i++) {
            ptr = posPlusOne(ptr, length);
        }
        return array[ptr];
    }
}