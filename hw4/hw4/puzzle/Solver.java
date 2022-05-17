package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;


public class Solver {
    private class SearchNode implements Comparable {
        private  WorldState state;
        private int movesSoFar;
        private SearchNode prev;

        public SearchNode(WorldState w, int m, SearchNode p) {
            state = w;
            movesSoFar = m;
            prev = p;
        }

        @Override
        public int compareTo(Object o) {
            if (o.getClass() == this.getClass()) {
                SearchNode n = (SearchNode) o;
                return this.movesSoFar + this.state.estimatedDistanceToGoal() -
                        n.movesSoFar - n.state.estimatedDistanceToGoal();
            }
            return -1;
        }
    }

    private MinPQ<SearchNode> sequenceNodes = new MinPQ<>();
    private SearchNode finalNode;
    private List<WorldState> answer = new ArrayList<>();

    public Solver(WorldState initial) {
        sequenceNodes.insert(new SearchNode(initial, 0, null));
        while (!sequenceNodes.isEmpty()) {
            SearchNode node = sequenceNodes.delMin();
            if (node.state.isGoal()) {
                finalNode = node;
                return;
            } else {
                for (WorldState neighbour : node.state.neighbors()) {
                    if (node.prev == null || !neighbour.equals(node.prev.state)) {
                        SearchNode n = new SearchNode(neighbour, node.movesSoFar + 1, node);
                        sequenceNodes.insert(n);
                    }
                }
            }
        }
    }
    public int moves() {
        return finalNode.movesSoFar;
    }
    public Iterable<WorldState> solution() {
        SearchNode tmp = finalNode;
        while (tmp != null) {
            answer.add(tmp.state);
            tmp = tmp.prev;
        }
        Collections.reverse(answer);
        return answer;
    }
}
