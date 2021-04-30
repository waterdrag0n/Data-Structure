public final class Node {
    public Node next;
    public Node prev;

    public int value;

    public Node() {
        next = null;
        prev = null;
        value = 0;
    }

    public Node(int value) {
        next = null;
        prev = null;
        this.value = value;
    }

    public Node(int value, Node prev, Node next) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    public Node getPrev() {
        return prev;
    }

    public Node getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }

    public void setPrev(Node p) {
        prev = p;
    }

    public void setNext(Node n) {
        next = n;
    }

    public void setValue(int v) {
        value = v;
    }
}
