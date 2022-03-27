package DataStructures;

public class DLL {
    private ListNode head, tail;
    int length;

    public DLL() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    public int getLength() {
        return length;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public void insertFirst(int val) {
        ListNode node = new ListNode(val);
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    public void insertLast(int val) {
        ListNode node = new ListNode(val);
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
    }

    class ListNode {
        int val;
        ListNode prev, next;

        ListNode(int value) {
            this.val = value;
            this.prev = null;
            this.next = null;
        }
    }
}

