package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    // Use another one to connect top, against backwash;
    private WeightedQuickUnionUF ufFull;
    //private WeightedQuickUnionUF ufTop;
    private boolean[] [] ifOpen;
    private int gridwith;
    private int openSites;
    // For simplification of full and percolation check, connected with every top site
    private int dummyTop;
    private int dummyBottom; // connected with every bottom site.

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.gridwith = N;
        ifOpen = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ifOpen[i][j] = false;
            }
        }
        openSites = 0;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufFull = new WeightedQuickUnionUF(N * N + 1);
        // WeightedQuickUnionUF has index from 0 to N - 1 (which is N*N + 1 here)
        dummyTop = gridwith * gridwith;
        dummyBottom = gridwith * gridwith + 1;
    }

    private int getPos(int row, int col) {
        return row * gridwith + col;
    }

    private boolean inGrid(int row, int col) {
        if (row < 0 || row > gridwith || col < 0 || col > gridwith) {
            return false;
        }
        return true;
    }

    private void connectNeighbours(int row, int col) {
        int newRow, newCol;
        int pos = getPos(row, col);
        int [] dx = {-1, 0, 1, 0};
        int [] dy = {0, -1, 0, 1};
        int direction = 4;
        for (int i = 0; i < direction; i++) {
            newRow = row + dx[i];
            newCol = col + dy[i];
            if (newRow < 0 || newCol < 0 || newRow >= gridwith || newCol >= gridwith) {
                continue;
            } // Shouldn't use inGrid here, because we don't want to be stuck by out of boundaries!ß
            if (isOpen(newRow, newCol)) {
                uf.union(pos, getPos(newRow, newCol));
                ufFull.union(pos, getPos(newRow, newCol));
            }
        }
    }

    public void open(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Not in the grid!");
        }
        if (ifOpen[row][col]) {
            return;
        }
        int pos = getPos(row, col);
        ifOpen[row][col] = true;
        connectNeighbours(row, col);
        if (row == 0) {
            uf.union(dummyTop, pos);
            ufFull.union(dummyTop, pos);
        } else if (row == gridwith - 1) {
            uf.union(dummyBottom, pos);
        }
        openSites++;
    }

    public boolean isOpen(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Not in the grid!");
        }
        return ifOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Not in the grid!");
        }
        int pos = getPos(row, col);
        return ufFull.connected(pos, dummyTop);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(dummyTop, dummyBottom);
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(0, 2);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(2, 0);

        System.out.println(perc.percolates());
    }
}
