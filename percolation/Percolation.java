/* *****************************************************************************
 *  Name: Faustine Ginoux
 *  Date: 14-01-2020
 *  Description: Coursera - Algo part I - Percolation assignment - Percolation class
 **************************************************************************** */

// compile: ctrl+B
// execute: ctrl+E
// open terminal: ctrl+T
// run in terminal: javac Percolation.java
// Best: compile from intelliJ and execute from terminal

// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf1;
    private boolean[][] grid;
    // private boolean[] gridflat;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The size must be positive!");
        }
        this.grid = new boolean[n][n]; // int default value = 0
        this.openSites = 0;
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2); // +2 for the two virtual sites
        this.uf1 = new WeightedQuickUnionUF(n * n + 1); // 1 virtual site to avoid backwash problem

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = false;
            }
        }
    }

    private void validateRowCol(int row, int col) {

        if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
            throw new IllegalArgumentException("Index out of bound.");
        }
    }

    private int toFlatGrid(int row, int col) {
        validateRowCol(row, col);
        return n * (row - 1) + col - 1; // 0 to n^2-1
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (this.n == 1) {
            this.grid[0][0] = true;
            this.openSites = 1;
            uf.union(0, 1);
            uf.union(1, 2);
            uf1.union(0, 1);
            return;
        }
        validateRowCol(row, col);
        if (this.grid[row - 1][col - 1]) {
            return;
        }
        this.grid[row - 1][col - 1] = true; // gridflat[n*(row-1)+col-1] = true;
        this.openSites += 1;
        if (row - 1 == 0) { // top row
            this.uf.union(0, toFlatGrid(row, col) + 1); //  +1: virtual top is 0
            this.uf1.union(0, toFlatGrid(row, col) + 1); //  +1: virtual top is 0
            if (col < this.n && isOpen(row, col + 1)) {
                uf.union(toFlatGrid(row, col + 1) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row, col + 1) + 1, toFlatGrid(row, col) + 1);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(toFlatGrid(row, col - 1) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row, col - 1) + 1, toFlatGrid(row, col) + 1);
            }
            if (isOpen(row + 1, col)) {
                uf.union(toFlatGrid(row + 1, col) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row + 1, col) + 1, toFlatGrid(row, col) + 1);
            }
        }
        else if (row == this.n) { // bottom row --> not uf1
            this.uf.union(this.n * this.n + 1, toFlatGrid(row, col) + 1); // n * (row - 1) + col);
            if (col < this.n && isOpen(row, col + 1)) {
                uf.union(toFlatGrid(row, col + 1) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row, col + 1) + 1, toFlatGrid(row, col) + 1);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(toFlatGrid(row, col - 1) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row, col - 1) + 1, toFlatGrid(row, col) + 1);
            }
            if (isOpen(row - 1, col)) {
                uf.union(toFlatGrid(row - 1, col) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row - 1, col) + 1, toFlatGrid(row, col) + 1);
            }
        }
        else {
            // connect to left if open, except if last on left
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(toFlatGrid(row, col - 1) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row, col - 1) + 1, toFlatGrid(row, col) + 1);
            }
            // connect to right if open, except if last on right
            if (col < this.n && isOpen(row, col + 1)) {
                uf.union(toFlatGrid(row, col + 1) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row, col + 1) + 1, toFlatGrid(row, col) + 1);
            }
            // connect to top if open; top row already handled
            if (isOpen(row - 1, col)) {
                uf.union(toFlatGrid(row - 1, col) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row - 1, col) + 1, toFlatGrid(row, col) + 1);
            }
            // connect to bottom if open; bottom row already handled
            if (isOpen(row + 1, col)) {
                uf.union(toFlatGrid(row + 1, col) + 1, toFlatGrid(row, col) + 1);
                uf1.union(toFlatGrid(row + 1, col) + 1, toFlatGrid(row, col) + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        /* if (this.n == 1) {
            return grid[0][0];
        } */
        validateRowCol(row, col);
        return this.grid[row - 1][col - 1]; // gridflat[n*(row-1)+col-1]
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        // with recursion:
        /* if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
            throw new IllegalArgumentException("Index out of bound.");
        }
        if (!this.grid[row - 1][col - 1]) { // !gridflat[n*(row-1)+col-1]
            return false;
        }
        if (row - 1 == 0) {
            return this.grid[row - 1][col - 1]; // always true since previous if
        }
        return isFull(row - 1, col) || isFull(row, col - 1) || isFull(row, col + 1);
         */
        // faster:
        return this.uf1.connected(0, toFlatGrid(row, col) + 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        /* for (int i = 0; i < this.n; i++) {
            if (isFull(this.n, i)) {
                return true;
            }
        }
        return false;
         */
        return this.uf.connected(0, this.n * this.n + 1); // top and bottom virtuals
    }

    // test client (optional)
    /* public static void main(String[] args) {

    } */
}
