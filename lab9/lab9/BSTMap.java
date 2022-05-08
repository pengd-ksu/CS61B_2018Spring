package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Peng Wang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) < 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        if (p.key.compareTo(key) == 0) {
            p.value = value;
        } else if (p.key.compareTo(key) < 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private void traverseTree(Node p, Set<K> keys) {
        if (p == null) {
            return;
        }
        keys.add(p.key);
        traverseTree(p.left, keys);
        traverseTree(p.right, keys);
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        traverseTree(root, keys);
        return keys;
    }

    private Node min(Node p) {
        Node cp = p; // So don't need to worry about changing parameter p;
        while (cp.left != null) {
            cp = cp.left;
        }
        return cp;
    }

    private Node removeMin(Node p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = removeMin(p.left);
        return p;
    }
    // No need to override, because return value is different
    // than that in Map61B interface
    private Node remove(K key, Node p) {
        int cmp = p.key.compareTo(key);
        if (cmp > 0) {
            p.left = remove(key, p.left);
        } else if (cmp < 0) {
            p.right = remove(key, p.right);
        } else {
            if (p.left == null) {
                return p.right;
            }
            if (p.right == null) {
                return p.left;
            }
            //need min node in p.left here;
            Node n = p;
            p = min(n.right);
            p.right = removeMin(n.right);
            //p.left = n.left;
        }
        return p;
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V val = get(key);
        if (val == null) {
            return null;
        }
        root = remove(key, root);
        size--;
        return val;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V val = get(key);
        if (val == null || val != value) {
            return null;
        }
        root = remove(key, root);
        size--;
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
