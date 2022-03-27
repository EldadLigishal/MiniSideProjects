package DataStructures;

public class FibonacciHeap {
    fibNode root = null;
    int size;
    private int minVal;

    FibonacciHeap() {

    }
}

class fibNode {
    int key, rank;
    boolean mark;

    fibNode parent, child, next, prev;

    fibNode() {
        this.rank = 0;
        this.mark = false;
        this.parent = null;
        this.child = null;
        this.next = null;
        this.prev = null;
        this.key = Integer.MAX_VALUE;
    }

    fibNode(int val) {
        super();
        this.key = val;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean getMark(fibNode node) {
        return this.mark;
    }

    public fibNode getParent() {
        return this.parent;
    }

    public void setParent(fibNode parent) {
        this.parent = parent;
    }

    public fibNode getChild() {
        return child;
    }

    public void setChild(fibNode node) {
        this.child = node;
    }

    public fibNode getNext() {
        return this.next;
    }

    public void setNext(fibNode node) {
        this.next = node;
    }

    public fibNode getPrev() {
        return prev;
    }

    public void setPrev(fibNode node) {
        this.prev = node;
    }

    public String toString() {
        return "key = " + this.key;
    }
}
