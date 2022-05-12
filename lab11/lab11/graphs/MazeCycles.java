package lab11.graphs;

/**
 *  @author Josh Hug
 */
// Remove '%' in %MazeType = POPEN_SOLVABLE for cycle detection
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        distTo[0] = 0;
        dfs(0);
    }

    public void dfs(int v) {
        marked[v] = true;
        announce();
        for (int u: maze.adj(v)) {
            if (marked[u]) {
                if (u != edgeTo[v]) {
                    return;
                }
            } else {
                edgeTo[u] = v;
                announce();
                distTo[u] = distTo[v] + 1;
                dfs(u);
            }
        }
    }

    // Helper methods go here
}

