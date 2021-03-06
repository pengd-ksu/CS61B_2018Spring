/**  first part of project1A.
 *   Deque implemented by Array
 *  @author PengWang
 */
public class LinkedListDeque<T> {
	private class Node { // Don't forget T here!
        private T item;
        private Node prev;
        private Node next;

        private Node(T i, Node pprev, Node nnext) {
            this.item = i;
            this.prev = pprev;
            this.next = nnext;
        }
        // For initialize snetinel. Because we don't know T type yet.
        private Node() {
            prev = null;
            next = null;
        }
    }
    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node node = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = node;
        sentinel.next = node;
        size++;
    }

    public void addLast(T item) {
        Node node = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node node = sentinel.next;
        while (node != sentinel) {
            System.out.print(node.item + " ");
            node = node.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T ans = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return ans;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T ans = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return ans;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node ans = sentinel;
        for (int i = 0; i <= index; i++) {
            ans = ans.next;
        }
        return ans.item;
    }

    private T getRecursiveHelper(int index, Node n) {
        if (index == 0) {
            return n.item;
        }
        return getRecursiveHelper(index - 1, n.next);
    }
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
}