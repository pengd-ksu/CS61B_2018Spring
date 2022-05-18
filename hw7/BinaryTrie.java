import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    private static class Node implements Serializable, Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        private Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
    private Node root;
    private Map<Character, BitSequence> table;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.insert(new Node(entry.getKey(), entry.getValue(), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        root = pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node node = root;
        for (int i = 0; i < querySequence.length(); i++) {
            if (node.isLeaf()) {
                return new Match(querySequence.firstNBits(i), node.ch);
            } else {
                if (querySequence.bitAt(i) == 0) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
        }
        return new Match(querySequence, node.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        table = new HashMap<>();
        buildLookupTableHelper(table, root, "");
        return table;
    }

    private void buildLookupTableHelper(Map<Character, BitSequence> t, Node x, String s) {
        if (x.isLeaf()) {
            t.put(x.ch, new BitSequence(s));
        }
        else {
            buildLookupTableHelper(t, x.left, s +'0');
            buildLookupTableHelper(t, x.right, s + '1');
        }
    }
}
