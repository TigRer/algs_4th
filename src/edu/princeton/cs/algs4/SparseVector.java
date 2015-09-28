/******************************************************************************
 *  Compilation:  javac SparseVector.java
 *  Execution:    java SparseVector
 *  Dependencies: StdOut.java
 *  
 *  A sparse vector, implementing using a symbol table.
 *
 *  [Not clear we need the instance variable N except for error checking.]
 *
 ******************************************************************************/

package edu.princeton.cs.algs4;

public class SparseVector {
    private int N;                   // length
    private ST<Integer, Double> st;  // the vector, represented by index-value pairs

    // initialize the all 0s vector of length N
    public SparseVector(int N) {
        this.N  = N;
        this.st = new ST<Integer, Double>();
    }

    // put st[i] = value
    public void put(int i, double value) {
        if (i < 0 || i >= N) throw new IndexOutOfBoundsException("Illegal index");
        if (value == 0.0) st.delete(i);
        else              st.put(i, value);
    }

    // return st[i]
    public double get(int i) {
        if (i < 0 || i >= N) throw new IndexOutOfBoundsException("Illegal index");
        if (st.contains(i)) return st.get(i);
        else                return 0.0;
    }

    // return the number of nonzero entries
    public int nnz() {
        return st.size();
    }

    // return the size of the vector
    public int size() {
        return N;
    }

    // return the dot product of this vector with that vector
    public double dot(SparseVector that) {
        if (this.N != that.N) throw new IllegalArgumentException("Vector lengths disagree");
        double sum = 0.0;

        // iterate over the vector with the fewest nonzeros
        if (this.st.size() <= that.st.size()) {
            for (int i : this.st.keys())
                if (that.st.contains(i)) sum += this.get(i) * that.get(i);
        }
        else  {
            for (int i : that.st.keys())
                if (this.st.contains(i)) sum += this.get(i) * that.get(i);
        }
        return sum;
    }


    // return the dot product of this vector and that array
    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : st.keys())
            sum += that[i] * this.get(i);
        return sum;
    }


    // return the 2-norm
    public double norm() {
        SparseVector a = this;
        return Math.sqrt(a.dot(a));
    }

    // return alpha * this
    public SparseVector scale(double alpha) {
        SparseVector c = new SparseVector(N);
        for (int i : this.st.keys()) c.put(i, alpha * this.get(i));
        return c;
    }

    // return this + that
    public SparseVector plus(SparseVector that) {
        if (this.N != that.N) throw new IllegalArgumentException("Vector lengths disagree");
        SparseVector c = new SparseVector(N);
        for (int i : this.st.keys()) c.put(i, this.get(i));                // c = this
        for (int i : that.st.keys()) c.put(i, that.get(i) + c.get(i));     // c = c + that
        return c;
    }

    // return a string representation
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : st.keys()) {
            s.append("(" + i + ", " + st.get(i) + ") ");
        }
        return s.toString();
    }


    // test client
    public static void main(String[] args) {
        SparseVector a = new SparseVector(10);
        SparseVector b = new SparseVector(10);
        a.put(3, 0.50);
        a.put(9, 0.75);
        a.put(6, 0.11);
        a.put(6, 0.00);
        b.put(3, 0.60);
        b.put(4, 0.90);
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a dot b = " + a.dot(b));
        StdOut.println("a + b   = " + a.plus(b));
    }

}
/******************************************************************************
 *  Copyright 2002-2015, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/