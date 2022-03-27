package DataStructures;

public class LinkedList<T> {

    private ListNode<T> head, tail;
    int size;

    LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /*
     * @complexity = O(1)
     * */
    public ListNode<T> head() {
        return this.head;
    }

    /*
     * @complexity = O(1)
     * */
    public ListNode<T> tail() {
        return this.tail;
    }

    /*
     * @complexity = O(1)
     * */
    public void prepend(T val) {
        ListNode<T> node = new ListNode<T>(val);
        if (this.size == 0) {
            this.head = node;
            this.tail = node;
        } else {
            ListNode<T> prevHead = this.head;
            this.head = node;
            this.head.next = prevHead;
        }
        this.size++;
    }

    /*
    * @complexity = O(1)
    * */
    public void append(T val) {
        ListNode<T> node = new ListNode<T>(val);
        if (this.size == 0) {
            this.head = node;
            this.tail = node;
        } else {
            ListNode<T> prevTail = this.tail;
            prevTail.next = node;
            this.tail = node;
        }
        this.size++;
    }

    /*
     * @complexity = O(1)
     * */
    public void popFirst() {
        if (this.size == 0) {
            return;
        }
        this.head = this.head.next;
        this.size--;
    }

    /*
     * @complexity = O(n)
     * */
    public void pop() {
        if (this.size == 0) {
            return;
        }
        ListNode<T> node = this.head;
        ListNode<T> prevNode = null;
        while (node.next != null) {
            prevNode = node;
            node = node.next;
        }
        this.tail = prevNode;
        prevNode.next = null;
        this.size--;
    }


    /*
     * @complexity = O(n)
     * */
    public void remove(ListNode<T> node) {
        ListNode<T> preNode = this.head;
        if (preNode == null) {
            return;
        } else if (node == this.head) {
            popFirst();
        }
        int cnt = 1;
        while ((preNode.next != node) && (preNode.next != null)) {
            preNode = preNode.next;
            cnt++;
        }
        preNode.next = preNode.next.next;
    }


    /*
     * @complexity = O(n)
     * */
    @Override
    public String toString() {
        String list = "";
        int i = 0;

        if(this.size == 0){
            return "List is empty";
        }

        ListNode<T> currNode = this.head;
        while (i < this.size){
            list = list.concat(currNode.toString());
            i++;
        }
        return list;
    }

    public static class ListNode<T> {
        T value;
        ListNode<T> next;

        ListNode(T val) {
            this.value = val;
            this.next = null;
        }

        @Override
        public String toString() {
            return "" + this.value + " ";
        }
    }
}
