package DataStructures;

import java.util.Arrays;

public class BinaryHeap {
    private int capacity;
    private int position;
    private int[] heap;

    //Min Heap
    BinaryHeap(){
        this.capacity = 10;
        this.position = 0;
        this.heap = new int[capacity];
    }

    BinaryHeap(int cap){
        this.capacity = cap;
        this.position = 1;
        this.heap = new int[capacity];
    }

    public int min() {
        return this.heap[0];
    }

    public int max() {
        return this.heap[position - 1];
    }

    public String printHeap() {
        return Arrays.toString(this.heap);
    }

    public void insert(int val){
        if (this.position == this.capacity) {
            System.out.println("Heap is full");
        } else {
            this.heap[position++] = val;

            int child = position - 1;
            int parent = (position - 1) / 2;

            //heapify-up
            while (this.heap[parent] > this.heap[child]) {
                int temp = this.heap[child];
                this.heap[child] = this.heap[parent];
                this.heap[parent] = temp;

                child = parent;
                parent = (child - 1) / 2;
            }
        }
    }

    public void deleteMin() {
        int pointer = 1;
        this.heap[0] = this.heap[position - 1];

        int parent = pointer;
        int child1 = 2*pointer;
        int child2 = 2*pointer + 1;

        //heapify-down
        while ((this.heap[parent] > this.heap[child1]) || (this.heap[parent] > this.heap[child2])) {
            int minChild;
            int tempIndexOfParent;
            if (this.heap[child1] >= this.heap[child2]) {
                minChild = child1;
                tempIndexOfParent = child1;
            } else {
                minChild = child2;
                tempIndexOfParent = child2;
            }

            int temp = this.heap[parent];
            this.heap[parent] = this.heap[minChild];
            this.heap[minChild] = this.heap[temp];

            parent = tempIndexOfParent;
            child1 = 2*tempIndexOfParent;
            child2 = 2*tempIndexOfParent + 1;
        }
        this.heap[position-1] = 0;
        position--;
    }

    public static void main(String[] args) {
        BinaryHeap heap1 = new BinaryHeap();
        for (int i=1; i<5 ;i++) {
            heap1.insert(i);
        }
        System.out.println(heap1.printHeap());
        heap1.deleteMin();
        System.out.println(heap1.printHeap());

        System.out.println(heap1.max());
        System.out.println(heap1.min());
    }
}

