package DataStructures;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Stack {
    private int capacity;
    private int[] myStack;
    private int pointer = -1;

    Stack() {
        this.capacity = 10;
        this.myStack = new int[capacity];
    }

    Stack(int size) {
        this.capacity = size;
        this.myStack = new int[capacity];
    }

    public int peek() {
        if (!isEmpty()) {
            return this.myStack[pointer];
        }
        return Integer.MIN_VALUE;
    }

    public boolean isFull() {
        return pointer - 1 == capacity;
    }

    public boolean isEmpty() {
        return this.pointer == -1;
    }

    public void push(int element) {
        // Check if the Stack is full
        if (!isFull()) {
            System.out.println("Stack is full sorry...");
        }
        this.myStack[++this.pointer] = element;
    }

    public void pop() {
        this.myStack[0] = 0;
        for (int i = 1; i < pointer; i++) {
            this.myStack[i - 1] = this.myStack[i];
        }
        --this.pointer;
    }
}
